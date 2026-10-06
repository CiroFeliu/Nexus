## Why

Variant **overhaul** (`feature/improve-design-overhaul`) sets `MOTION_INTENSITY 6`. Today the only motion on the site is the scroll-to-section animation. A motion layer that communicates hierarchy (hero entry), sequence (sections revealing as they arrive), feedback (hover/press on projects and links) and state (active nav, menu open) makes the overhaul feel finished. It is a separate change so the static overhaul layouts can be reviewed first and the motion judged on its own.

## What Changes

- Hero entry choreography: role, name lines, bio, actions and portrait fade/rise in sequence once per app start.
- Section reveal: each section's headline and first content block fade/rise the first time they enter the viewport; never replays when scrolling back.
- Project tiles: hover lift + a spotlight border that follows the pointer on pointer platforms; pressed scale; touch platforms get pressed feedback only.
- Portrait parallax: the hero portrait moves slightly slower than the scroll (transform only).
- Navigation: underline slides between links; bar background transitions between transparent and solid; compact menu opens with a staggered link entry.
- Everything collapses to instant/static under the reduced-motion preference, and no content depends on an animation to become visible or usable.

## Capabilities

### New Capabilities
- `portfolio-motion`: rules for which elements animate, how motion is driven, and how it degrades under reduced motion.

### Modified Capabilities
- (none)

## Impact

- New `theme/motion/` helpers (reveal, spotlight, parallax modifiers) in `commonMain`
- `HeroAboutSection.kt`, `ProjectsSection.kt`, section headers, `PortfolioTopBar`
- Depends on `refine-design-foundation` (motion tokens, `LocalReducedMotion`) and `overhaul-hero-and-navigation` / `overhaul-section-layouts`
- No new dependencies
