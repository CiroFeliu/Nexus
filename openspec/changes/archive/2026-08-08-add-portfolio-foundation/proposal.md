## Why

The `:shared` module is still the default JetBrains KMP wizard scaffold (a "Click me!" demo button, no theme, no navigation). Before any portfolio content can be built, we need a shared design system (colors, typography, dark mode) and a navigation shell that all four targets (`:webApp`, `:androidApp`, `:iosApp`, `:desktopApp`) render identically. This is a blocking prerequisite: the five content-section changes (Hero/About, Skills, Experience, Projects, Contact) can only be parallelized across agents once this foundation exists, since they'd otherwise all need to touch the same theme/navigation files.

## What Changes

- Replace the wizard demo (`App.kt`, `Greeting.kt`, `GreetingUtil.kt`) with a real app entry point.
- Add a Material3-based design system: light/dark color schemes, typography scale, spacing/shape tokens, following system Dark Mode preference by default.
- Add a navigation shell that defines the portfolio's sections (Hero/About, Skills, Experience, Projects, Contact) as addressable routes/destinations, with a placeholder screen per section.
- Wire the shell into all four platform entry points (`webApp` main.kt, `androidApp` MainActivity, `desktopApp` main, `iosApp` MainViewController) so each target renders the same navigation shell out of the box.
- Add a shared `openUrl(url: String)` platform capability (`expect`/`actual`), since both the Projects and Contact sections need to open external links and it should exist in exactly one place, not be duplicated by two parallel changes.

## Capabilities

### New Capabilities
- `design-system`: Shared Material3 theme (light/dark color schemes, typography, spacing/shape tokens) used by every composable in the portfolio.
- `navigation-shell`: Shared definition of portfolio sections as navigable destinations, plus the scaffold (top-level layout, section switching) that renders them across all four targets.
- `external-link-opening`: A shared `openUrl(url: String)` capability, implemented per-platform, for sections that need to open external links (repo/demo links, social/contact links).

### Modified Capabilities
(none — this is the first change in the project)

## Impact

- `:shared/src/commonMain` — new theme package, new navigation package, `App.kt` rewritten to compose them together; wizard demo files (`Greeting.kt`, `GreetingUtil.kt`, demo drawable) removed.
- `:shared` per-platform `Platform.*.kt` files (`androidMain`, `iosMain`, `jvmMain`, `jsMain`, `wasmJsMain`) — add one `actual openUrl` implementation each.
- `:webApp`, `:androidApp`, `:desktopApp` entry points, and `:iosApp` `MainViewController.kt` — updated only if the shell's public entry signature changes; otherwise unaffected since they already call the shared `App()`.
- No new external dependencies expected (Material3 is already on the `:shared` classpath).
