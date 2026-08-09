## Why

The portfolio is a single long scroll with no way to jump directly to a section — a recruiter who wants to skip straight to Projects or Contact has to scroll past everything else. The current `navigation-shell` spec explicitly chose *not* to have anchor navigation, keeping only the language switcher visible at all scroll positions. That was the right call for the MVP (fewer moving parts while sections were still being built), but now that all five sections are implemented, the lack of in-page navigation is a real usability gap worth reversing before publishing.

## What Changes

- Add a sticky header, alongside the existing `LanguageSwitcher`, with anchor links to each declared `PortfolioSection` (Hero/About, Skills & Stack, Experience, Projects, Contact).
- Clicking a link scrolls the page to that section (smooth scroll on web; immediate scroll on other targets where smooth scrolling isn't idiomatic).
- Highlight the link matching the section currently in view as the visitor scrolls.
- **BREAKING** (spec-level, not code): supersedes the `navigation-shell` requirement "Shell hosts the language-switcher control ... without introducing a section-anchor navigation system" — that constraint is removed in favor of the new nav requirements below.

## Capabilities

### New Capabilities
- `section-navigation`: sticky in-page navigation with anchor links to each portfolio section, active-section highlighting, and scroll-to-section behavior.

### Modified Capabilities
- `navigation-shell`: the requirement "Shell hosts the language-switcher control" is replaced — the shell now hosts both the language switcher and the section-navigation control together, and the "without introducing a section-anchor navigation system" constraint is removed.

## Impact

- `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/PortfolioShell.kt`: renders the new nav control alongside `LanguageSwitcher`.
- `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/PortfolioSection.kt`: likely needs a stable per-section anchor/id and a display label if it doesn't already expose one.
- New file(s) under `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/` for the nav control itself.
- No changes to individual section content files — each section keeps owning its own file per the existing `navigation-shell` isolation requirement.
