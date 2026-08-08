## Why

`add-portfolio-foundation` creates `navigation/sections/HeroAboutSection.kt` with a placeholder body. This change fills it in with Ciro's actual introduction — the first thing any visitor sees — so the portfolio has a real first impression instead of a "TODO" marker.

**Depends on:** `add-portfolio-foundation` must be applied first (needs `PortfolioTheme`, `SectionPlaceholder`, and the `HeroAboutSection` file to exist).

## What Changes

- Implement `HeroAboutSection.Content()`: name, role title ("Senior Android Developer & Mobile Systems Architect"), a short bio, and an avatar/photo placeholder image.
- Use only shared theme tokens (`theme/`) for colors/typography — no new hardcoded styling.

## Capabilities

### New Capabilities
- `hero-about-section`: The Hero/About section's content — identity, role, short bio, avatar.

### Modified Capabilities
(none)

## Impact

- `:shared/src/commonMain/navigation/sections/HeroAboutSection.kt` only. No shell, registry, or other section files are touched.
- May add a small `navigation/sections/heroabout/` sub-package if the layout needs helper composables, but nothing outside this section's own tree.
