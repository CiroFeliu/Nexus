# Proposal

## Why

Ciro wants to publish technical posts (the first ones about ShogunAi) on cirofeliu.es. The portfolio is a Compose/Wasm canvas with no URL routing, served by a plain static file server with no SPA fallback, so a blog rendered inside the Compose app would have no indexable pages, no working deep links and no native `<video>` or text selection in code blocks. Posts need real HTML pages at stable URLs that ship through the existing deploy unchanged.

## What Changes

- Posts are authored as Markdown with YAML front matter, one folder per post under `blog/posts/<slug>/index.md` at the repository root, with their media next to them.
- A build-time generator (JVM, inside Gradle) turns those posts into static HTML inside the web distribution: `/blog/` (one list of every published post, each marked with its language), `/blog/<slug>/` (one page per post) and `/blog/feed.xml` (RSS 2.0). The existing rsync deploy publishes them with no workflow or server change.
- Image syntax pointing at a `.mp4`/`.webm` file renders a native `<video>` figure with its caption; pointing at a YouTube URL renders a privacy-enhanced (youtube-nocookie) embed; any other image renders as an image.
- Fenced code blocks keep their language tag as a class so they can be highlighted later; build-time highlighting is not part of this change.
- Translations are separate posts sharing a `translationKey`; each post links to its published other-language version.
- Drafts (`draft: true`) are left out of the generated pages and the feed.
- Invalid front matter or broken post structure fails the build with a message that names the post and the problem.
- `/blog/` shows an empty state when no post is published, which is the expected state when this change ships.
- Generated pages carry their own stylesheet reproducing the portfolio's design tokens (Geist fonts, colours, light/dark via `prefers-color-scheme`).
- The Compose top bar and the compact menu get a "Blog" entry that opens the blog through the shared URL-opening capability: `/blog/` on the web, `https://cirofeliu.es/blog/` on Android, iOS and desktop.

## Capabilities

### New Capabilities

- `blog-authoring`: the content contract for posts (folder layout, front matter fields, media references, drafts, translations) and the build-time validation that enforces it.
- `blog-site`: the static blog generated into the web distribution — list page, post pages, RSS feed, media and code rendering, language marking and translation links, empty state and styling.

### Modified Capabilities

- `section-navigation`: the navigation bar and the compact menu gain a Blog entry that leaves the single-page portfolio for the blog.

## Impact

- New Gradle module `:blogGenerator` (Kotlin/JVM) with its own unit tests, run by `./gradlew test`; new dependencies commonmark-java and snakeyaml-engine, used only at build time and never shipped to the browser.
- `webApp/build.gradle.kts` wires the generator's output into the web resources so both `wasmJsBrowserDistribution` (deploy) and the development server include `/blog/`.
- New repository folder `blog/` holding the posts and a short authoring guide; no posts are added by this change.
- `:shared`: `PortfolioTopBar`, `CompactNavigationMenu` and `PortfolioShell` gain the Blog entry; each platform's `Platform.*.kt` provides the blog URL.
- No change to `.github/workflows/deploy.yml` or to the server.
