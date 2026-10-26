# Tasks

## 1. Generator module setup

- [x] 1.1 Add `commonmark`, `commonmark-ext-gfm-tables`, `commonmark-ext-gfm-strikethrough` and `snakeyaml-engine` to `gradle/libs.versions.toml`, create `:blogGenerator` (Kotlin/JVM, `kotlin.test` + JUnit) and include it in `settings.gradle.kts`; verify `./gradlew :blogGenerator:build` succeeds

## 2. Content parsing and validation

- [x] 2.1 Discover post folders under the posts root (missing root or empty → no posts; non-kebab-case slug or missing `index.md` → failure naming the folder); verify with unit tests for each `blog-authoring` "One folder per post" scenario
- [x] 2.2 Split and parse front matter into `PostMeta` with all contract rules (required fields, ISO date, `en`/`es`, booleans, tag list, unknown keys, missing block), reporting every problem of a post in one message; verify with unit tests for each "Front matter fields" scenario
- [x] 2.3 Resolve translations (shared `translationKey`, conflict on same key + lang, drafts excluded from pairing) and published-post selection with the `includeDrafts` flag; verify with unit tests for "Drafts are not published" and "Translations share a key"

## 3. Markdown rendering

- [x] 3.1 Classify image destinations (self-hosted `.mp4`/`.webm`, YouTube `watch?v=`/`youtu.be` with id extraction, image) and validate relative media (exists, stays in the folder); verify with unit tests including the unrecognised-YouTube, missing-file and `..` cases
- [x] 3.2 Render the body with commonmark (GFM tables, strikethrough, escaped raw HTML, sanitised URLs) and custom renderers for video/YouTube figures, lazy images and standalone-paragraph enforcement; verify with unit tests for the video, YouTube, code block, raw `<script>` and inline-video scenarios

## 4. Site generation

- [x] 4.1 Add `blog.css` (portfolio tokens, light/dark, reading column, phone gutters, scrolling code blocks, responsive media) and a test that its colours match `shared/src/commonMain/kotlin/app/luxion/nexus/theme/Color.kt`; verify the test passes
- [x] 4.2 Generate the list page (ordering, language badge, tags, empty state), post pages (document language, localised labels, translation link + `hreflang` alternates, draft badge when previewing) and SEO metadata (title, description, canonical, Open Graph, feed link); verify with unit tests on the generated HTML
- [x] 4.3 Generate `feed.xml` (RSS 2.0, absolute links, guid, RFC 1123 dates, deterministic `lastBuildDate`, valid when empty) and copy fonts and published posts' media; verify with unit tests, including that drafts' media are not copied
- [x] 4.4 Add the `main` entry point and an end-to-end test that generates a fixture posts folder (`src/test/resources/fixtures/`, published en/es translation pair with a video, a YouTube embed and code, plus a draft) twice into temp dirs and checks the expected files and byte-identical output

## 5. Build wiring

- [x] 5.1 Register `generateBlog` (`JavaExec`) in `webApp/build.gradle.kts` with posts/fonts inputs, `blog.drafts` property and the output added to `webMain` resources; verify `./gradlew :webApp:wasmJsBrowserDistribution` puts `blog/index.html` (empty state), `blog/feed.xml` and `blog/assets/` in `webApp/build/dist/wasmJs/productionExecutable/`
- [x] 5.2 Add `blog/README.md` (authoring guide: folder layout, front matter, video/YouTube syntax, drafts, `-Pblog.drafts=true` preview, media size advice) and a "Blog" section in the root `README.md`; verify the documented preview command works with a temporary local post that is deleted afterwards

## 6. Navigation entry

- [x] 6.1 Add `expect val blogUrl` to `Platform.kt` with actuals in every `Platform.*.kt` (`/blog/` on JS/Wasm, `https://cirofeliu.es/blog/` on Android, iOS, JVM); verify all targets compile (`./gradlew :shared:compileKotlinJvm :shared:compileKotlinWasmJs :shared:compileKotlinJs`, plus iOS/Android metadata compile)
- [x] 6.2 Add the Blog entry to `PortfolioTopBar` (after `SectionNavigation`, with the open-in-new icon) and `CompactNavigationMenu`, wired from `PortfolioShell` to close the menu and `openUrl(blogUrl)`; verify with Compose UI tests that both show the entry and invoke `onBlogSelected`

## 7. Integration checks

- [x] 7.1 Run `./gradlew test` and `openspec validate add-blog-section --strict`; both pass (`:shared` and `:blogGenerator` tests pass; `./gradlew test` itself stops at `:androidApp:checkDebugAarMetadata`, which already fails on `develop` because the dependabot bumps need compileSdk 37 / AGP 9.1 — unrelated to this change)
- [x] 7.2 Run the web app with a temporary local post (removed afterwards), check `/blog/` and the post page in light and dark, at phone and desktop widths, the video and YouTube embeds, the RSS feed, and the Blog entry in the bar and the compact menu

## Workflow follow-up

- Message the ShogunAi session to confirm the content contract and the clarifications (video on its own line, raw HTML escaped, unknown front matter keys rejected).
- Archive the change in the branch after validation, then open the PR to `develop` assigned to CiroFeliu.
