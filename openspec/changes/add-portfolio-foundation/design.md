## Context

`:shared/src/commonMain` currently only contains the JetBrains KMP wizard demo (`App.kt` with a "Click me!" button, `Greeting.kt`/`GreetingUtil.kt`, a sample drawable). All four platform modules (`webApp`, `androidApp`, `desktopApp`, `iosApp`) already call the shared `App()` composable as their entry point, so no platform-specific wiring changes are expected beyond what's needed to keep compiling. Compose Multiplatform 1.11.1 / Material3 1.11.0-alpha07 are already on the `:shared` classpath (`gradle/libs.versions.toml`).

## Goals / Non-Goals

**Goals:**
- A single Material3 theme (colors, typography, shapes, spacing tokens) shared by every composable, with automatic light/dark switching based on system preference.
- A navigation shell that declares the portfolio's sections as destinations and can render each one behind a placeholder, ready for the parallel content changes to fill in.
- Zero behavior divergence across targets: the same shell renders identically on Web, Android, iOS, and Desktop.

**Non-Goals:**
- Actual section content (Hero/About, Skills, Experience, Projects, Contact bodies) — each is its own follow-up change.
- Deployment, CI, hosting — explicitly deferred by Ciro until the site is further along.
- A user-facing manual theme toggle — following system Dark Mode preference is enough for v1; a manual override can be a later enhancement if desired.

## Decisions

- **Single-page scroll navigation over multi-route navigation**: sections are modeled as an ordered list of destinations rendered in one scrollable column (like a classic portfolio one-pager), not as separate browser routes with deep-linking. Rationale: Compose Multiplatform's web routing/deep-link story is less mature than native Navigation, and a portfolio's primary UX is scroll-and-skim, not URL-addressable sub-pages. A `PortfolioSection` sealed model plus a `LazyColumn`-based shell keeps this simple and testable, while still exposing anchors that could later back real URL fragments if needed.
- **Theme lives in its own package (`theme/`)**, separate from `navigation/`, so the two capabilities in this change stay independently reviewable and so later section changes only need to depend on `theme`, not `navigation` internals.
- **Dark mode via `isSystemInDarkTheme()`** (Compose's cross-platform system-preference API) rather than a custom preference store — matches "native Dark Mode support" from the brief with the least code.
- **Remove wizard demo files outright** (`Greeting.kt`, `GreetingUtil.kt`, `Platform.kt` usages tied to the demo, demo drawable) rather than leaving them dead in the tree — nothing in the brief needs a "platform name" greeting.
- **One file per section, created here with placeholder content**: this change creates `navigation/sections/HeroAboutSection.kt`, `SkillsSection.kt`, `ExperienceSection.kt`, `ProjectsSection.kt`, and `ContactSection.kt`, each a small object/composable exposing `Content()` that renders a placeholder body. A separate, rarely-touched `PortfolioSection.kt` registry just lists these five in order for the shell to iterate. This is deliberate: the five follow-up content changes each edit only their own section file's `Content()` body, so parallel agents never touch the same file or the shell/registry — avoiding merge conflicts by construction.
- **`openUrl` lives in the foundation, not in Projects or Contact**: both of those sections need to open external links; defining the `expect`/`actual` capability once here (rather than letting either section add it) avoids two parallel changes racing to add the same declaration to the same `Platform.*.kt` files.

## Risks / Trade-offs

- [Risk] Placeholder sections with no real content make this change hard to visually validate → Mitigation: each placeholder renders its section title and an explicit "TODO" marker so it's obvious in a build/screenshot that content is pending, and the five follow-up changes are scoped 1:1 to these placeholders.
- [Risk] A future decision to add real per-section URLs (e.g. `/projects`) would require revisiting the single-page-scroll decision → Mitigation: keep `PortfolioSection` as a clean enum/sealed list now so swapping the rendering strategy later doesn't require touching section content.
- [Risk] Material3 on Compose Web is comparatively newer/less battle-tested than on Android → Mitigation: keep the theme surface small (colors/typography/shapes) and avoid Material3 components with known Web gaps; validate by running `:webApp:wasmJsBrowserDevelopmentRun` as part of this change's tasks.

## Open Questions

- Should section anchors eventually back real deep-linkable URLs on Web? Deferred — not needed for v1, revisit once content exists.
