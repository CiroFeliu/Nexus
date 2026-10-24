# Design

## Context

See proposal.md for motivation. The web app is a single Compose canvas: `webApp/src/webMain/resources` (`index.html`, `styles.css`, favicon, OG image) is copied into `webApp/build/dist/wasmJs/productionExecutable/`, and the deploy rsyncs that folder as is (`--delete`, `.map` excluded) to a static file server. The server cannot be changed for this work and has no SPA fallback, so any route the blog needs must exist as a real file. Directory requests (`/blog/x/`) are served from `index.html`, the convention the generator follows.

The portfolio's tokens live in Kotlin (`theme/Color.kt`, `theme/Typography.kt`) and the Geist/Geist Mono TTFs in `shared/src/commonMain/composeResources/font/`. `openUrl(url)` is the shared way to leave the app (`window.open(url, "_blank")` on the web).

The content contract (folder per post, front matter fields, video syntax) was agreed with the ShogunAi session, which will author the first posts; it is fixed by `specs/blog-authoring`.

## Goals / Non-Goals

**Goals:**

- Real, crawlable HTML at `/blog/`, `/blog/<slug>/` and `/blog/feed.xml`, produced by `./gradlew :webApp:wasmJsBrowserDistribution` with no workflow change.
- Fail fast and loudly on bad content; a broken post never reaches production half-rendered.
- Generator logic testable as plain JVM unit tests under `./gradlew test`.

**Non-Goals:**

- Build-time syntax highlighting (code keeps a `language-<tag>` class so it can be added later).
- Pagination, tag pages, search, comments, a sitemap.
- Rendering posts inside the Compose app, or per-language list pages.
- A JS-target (`jsBrowserDistribution`) deploy; the blog is wired into `webMain` resources, so it is there too, but only the Wasm distribution is deployed.

## Decisions

### A dedicated `:blogGenerator` Kotlin/JVM module run by a `JavaExec` task

The generator is a normal module (`blogGenerator/`, plugin `kotlinJvm`, package `app.luxion.nexus.blog`) with a `main` that takes the posts folder, the fonts folder, the output folder, the site base URL and an `includeDrafts` flag. `webApp` resolves its runtime classpath through a dedicated resolvable configuration (`java-runtime` usage attributes) and registers `generateBlog` (`JavaExec`) with declared inputs (posts tree, fonts) and output (`build/generated/blog`). The output folder is added as a resource directory of `webMain` via the task provider, so Gradle wires the dependency for both the Wasm/JS distribution and the dev server.

- *Alternative: `buildSrc` / included-build plugin.* Heavier to test and to evolve, and its tests would not run under the root `./gradlew test` the deploy relies on. A module gets `test` for free.
- *Alternative: a Kotlin script or a shell tool (Hugo, Pandoc).* Adds a non-Gradle toolchain to CI and to every contributor's machine.

### Parsing: snakeyaml-engine for front matter, commonmark-java for the body

The front matter block (between the first two `---` lines) is split off by hand and parsed with snakeyaml-engine (YAML 1.2, so `2026-11-03` stays a string and is parsed with `LocalDate.parse`, and `no`/`yes` are not booleans). Validation maps it to a `PostMeta` data class, collecting every problem of a post into one `BlogContentException` message (`blog/posts/<slug>/index.md: missing required field 'summary'`). Unknown keys are rejected to catch typos.

The body is parsed with commonmark-java plus the GFM tables and strikethrough extensions. Rendering uses `HtmlRenderer` with `escapeHtml(true)` and `sanitizeUrls(true)` (spec: untrusted markup is neutralised). Before rendering, a visit of the AST classifies every image destination as self-hosted video (`.mp4`/`.webm`), YouTube (`youtube.com`/`www.`/`m.` `watch?v=`, `youtu.be/`; id `[A-Za-z0-9_-]{11}`) or image, and checks relative media (exists, stays inside the post folder, only `http`/`https` schemes). A paragraph whose only child is a video/YouTube image is replaced by a custom `EmbedBlock` node, so the figure is emitted without an invalid `<p><figure>` wrapper; a video image anywhere else fails the build. A custom node renderer emits `EmbedBlock` as `<figure>` (video or youtube-nocookie iframe plus caption) and `Image` as `<img loading="lazy" alt>`. Fenced code keeps commonmark's default `class="language-<tag>"`. All problems are collected before any output is written.

- *Alternative: commonmark's YAML front matter extension.* It returns strings only and accepts invalid YAML silently.
- *Alternative: flexmark.* Larger, and nothing here needs its extras.

### HTML produced with small Kotlin string templates

Pages are built by plain functions (`listPage`, `postPage`, `feed`) using `buildString` and one `escape` helper; no template engine. The site is three page types; a dependency would cost more than it saves. All dynamic text goes through the escaper; only the commonmark output is inserted raw.

Interface labels (back link, "Read in Spanish", empty state, feed title) live in a small `BlogLabels` map keyed by `en`/`es`. The list page is in English (`lang="en"`), since it mixes both languages and the portfolio defaults to English; each post page uses its own language.

Dates: `YYYY-MM-DD` in a `<time datetime>` with a human form (`3 Nov 2026` / `3 nov 2026`); RSS uses RFC 1123 at 00:00 UTC. `lastBuildDate` is the newest post's date (or omitted when empty), never "now", so output is deterministic and `rsync --checksum` sees no spurious changes.

### URLs and assets

- `/blog/index.html`, `/blog/<slug>/index.html`, `/blog/feed.xml`, `/blog/assets/blog.css`, `/blog/assets/fonts/*.ttf`.
- Internal links are root-relative (`/blog/<slug>/`, `/`), so the dev server and production behave the same; canonical, OG and feed links are absolute against the base URL `https://cirofeliu.es`.
- Media files of a published post are copied verbatim next to its `index.html` and referenced by their relative name.
- Fonts are copied from `shared/src/commonMain/composeResources/font/` (single source, declared as task input) rather than duplicated in the repo or loaded from a CDN.

### Stylesheet reproduces the tokens by hand

`blog.css` is a static resource of `:blogGenerator` with CSS custom properties copied from `PortfolioLightColorScheme`/`PortfolioDarkColorScheme` (background, surface, onSurface, onSurfaceVariant, primary, outlineVariant, surfaceContainer) and the type scale's key sizes, switched by `@media (prefers-color-scheme: dark)`. A reading column (~68ch), `pre { overflow-x: auto }`, `video, img, iframe { max-width: 100% }`, a 16px side gutter on phones. A `:blogGenerator` test guards against drift by reading `shared/.../theme/Color.kt` as text and asserting that every colour role used in `blog.css` has the same hex value in the matching light/dark scheme.

- *Alternative: generate CSS from the Kotlin tokens.* Requires `:blogGenerator` to depend on Compose. Not worth it for seven colours.

### Drafts

Production never includes drafts. `generateBlog` reads a Gradle property `blog.drafts` (default `false`) and passes the paths and flag as plain arguments (configuration-cache safe); `./gradlew :webApp:wasmJsBrowserDevelopmentRun -Pblog.drafts=true` previews drafts locally, marked with a "Draft" badge. The deploy never passes it.

### Blog entry in the Compose navigation

A `BlogNavLink` composable (same visual language as `NavLink`, plus the existing `ic_open_in_new` icon to signal it leaves the page) is placed after `SectionNavigation` in `PortfolioTopBar`, behind a thin vertical separator; in `CompactNavigationMenu` a divider and a `MenuLink` with the same icon follow the section links. Both take an `onBlogSelected: () -> Unit`; `PortfolioShell` passes `{ menuOpen = false; openUrl(blogUrl) }`. `blogUrl` is an `expect val` added to `Platform.kt` with actuals in the existing `Platform.*.kt` files (`"/blog/"` on JS/Wasm, `"https://cirofeliu.es/blog/"` elsewhere). Labels stay "Blog" in both languages through the existing label-map pattern.

On the web this opens the blog in a new tab (current `openUrl` behaviour), which keeps the portfolio and its scroll position intact.

### Tests

- `:blogGenerator` (`kotlin.test` on JUnit): front matter validation (each rule), slug rule, media classification and YouTube id extraction, paragraph/figure rendering, HTML escaping, translation pairing, draft exclusion, ordering, feed shape, empty state, and an end-to-end generation of a fixture posts folder under `src/test/resources/fixtures/` (including a tiny video placeholder file) into a temp dir.
- `:shared`: the top bar and compact menu show a Blog entry whose activation calls `onBlogSelected`.
- No real post is added; the fixture lives only in test resources.

## Risks / Trade-offs

- [Blog CSS drifts from the Compose theme when colours change] → The colour-drift test fails the build until `blog.css` is updated.
- [Large videos inflate the repo and every deploy] → Authoring guide in `blog/README.md` recommends short, compressed clips and YouTube for long ones; rsync only transfers changed files.
- [Escaping raw HTML blocks legitimate embeds an author may want] → Supported embeds (video, YouTube) have Markdown syntax; anything else is a contract change to agree with the authors.
- [New tab on the web for a same-site link] → Accepted to reuse `openUrl`; can switch to same-tab later without spec change.
- [Static server's handling of `/blog/<slug>` without trailing slash] → All generated links use the trailing slash; canonical URLs too.

## Migration Plan

Additive. Merging to `master` deploys `/blog/` with the empty state. Rollback is reverting the merge; rsync `--delete` removes the folder.
