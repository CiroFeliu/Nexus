## Why

Variant **overhaul** of the design comparison (`feature/improve-design-overhaul`): a new visual language on top of the existing content, sections, order and labels. Dials: `DESIGN_VARIANCE 7 / MOTION_INTENSITY 6 / VISUAL_DENSITY 3`, still within "clean, technical, structured, modern" but with an editorial, confident first impression.

Today's first viewport (two stacked nav bars, centered 96dp circular avatar, centered ~60-word paragraph, no action) is the most generic part of the site, and the part recruiters judge in seconds.

## What Changes

- **Editorial asymmetric hero** that owns the first viewport: oversized name in Geist display (two lines max, tight tracking) aligned to a left column, role as a single Geist Mono line above it, bio <= 20 words, primary "Download CV" + secondary "Contact" text link. A tall 4:5 portrait sits in a right column, offset downward so it overlaps the boundary with the next section, giving depth. A subtle grain texture on a fixed overlay layer adds material without gradients.
- **Floating minimal navigation**: transparent over the hero, turning into a solid `surfaceContainer` bar with a hairline once scrolled; name wordmark left, links + `EN | ES` right on one line; animated active underline. On compact widths a menu button opens a full-screen overlay with large section links.
- Hero entry choreography (name, role, bio, actions, portrait) is defined here as states; the actual animation implementation lives in `add-motion-layer`.

## Capabilities

### New Capabilities
- (none)

### Modified Capabilities
- `hero-about-section`: adds hero actions, first-viewport fit and an asymmetric, width-adaptive layout.
- `section-navigation`: adds the single floating bar and the compact full-screen menu.

## Impact

- `navigation/PortfolioShell.kt` (bar overlays content instead of sitting above it; scroll-to-section callback for the hero), `SectionNavigation.kt`, `i18n/LanguageSwitcher.kt`
- `navigation/sections/HeroAboutSection.kt`
- New grain overlay composable in the theme package
- Depends on `refine-design-foundation`, `polish-portfolio-copy`; animations via `add-motion-layer`
