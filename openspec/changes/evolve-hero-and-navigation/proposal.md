## Why

Variant **evolve** of the design comparison (`feature/improve-design-evolve`): keep the page structure, sections, accent and voice, and fix the first impression with low risk.

Today the first viewport is two stacked bars (section links in a `FlowRow` that can wrap, then a separate `EN / ES` row), followed by a fully centered hero: a 96dp circular avatar, the name, the role and a ~60-word centered paragraph, with no action to take. The hero has no real visual weight and no way forward besides scrolling.

Dials for this variant: `DESIGN_VARIANCE 5 / MOTION_INTENSITY 4 / VISUAL_DENSITY 4` ("clean, technical, structured, modern" developer portfolio).

## What Changes

- **One navigation bar** (64dp): name wordmark (Geist 600 text, not a drawn logo) on the left, section links and the language toggle on the right, always one line. Active section shown with an accent underline that animates between links. A hairline separator appears once the page is scrolled. On compact widths the links move into a menu opened from a single button.
- **Split hero** at medium+ widths: left-aligned text column (name, role, short bio, actions) and a larger portrait on the right (rounded rectangle from the shape system, not a circle). Compact widths stack photo above text, left-aligned.
- **Hero copy:** bio cut to <= 20 words per language (Ciro to approve wording); the long version is dropped from the hero.
- **Hero actions:** primary "Download CV"/"Descargar CV" (same label and action as Contact), secondary text link "Contact"/"Contacto" that scrolls to the Contact section.
- The hero fits the first viewport at 1280x720 and gets a single short entry animation (fade + rise), skipped under reduced motion.

## Capabilities

### New Capabilities
- (none)

### Modified Capabilities
- `hero-about-section`: adds requirements for hero actions, first-viewport fit and width-adaptive layout.
- `section-navigation`: adds the single-bar requirement and the compact menu behaviour.

## Impact

- `navigation/SectionNavigation.kt`, `i18n/LanguageSwitcher.kt`, `navigation/PortfolioShell.kt` (one bar instead of two, scroll-to-section callback shared with the hero)
- `navigation/sections/HeroAboutSection.kt`
- Builds on the archived foundation (tokens, interactive modifier, reduced motion) and copy changes
- Uses the current portrait asset (`hero_photo.jpg`)
- Tests: `SectionNavigationTest`, `PortfolioShellTest`, hero smoke test updated for the new structure
