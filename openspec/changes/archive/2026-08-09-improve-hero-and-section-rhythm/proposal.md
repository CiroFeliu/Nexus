## Why

The portfolio's first impression is a flat colored circle with two-letter initials, and every section afterward (Hero, Skills, Experience, Projects, Contact) sits on the exact same flat background with no visual separation — the page reads as one long undifferentiated block of text rather than distinct, scannable sections. The existing `hero-about-section` spec already anticipated a real photo (its avatar requirement covers both "initials-based placeholder or photo"), but no photo path has ever been implemented — the app only ever renders initials.

## What Changes

- Add support for a real headshot photo in the Hero/About avatar, using the existing `compose.components.resources` dependency (already in `shared/build.gradle.kts`, unused so far) so the image ships cross-platform via `composeResources` — no new dependency.
- Keep the initials avatar as the fallback per the existing `hero-about-section` scenario "No photo asset supplied yet" — this remains true until a photo file is actually added to the resources folder.
- Introduce visual rhythm between sections: alternate `MaterialTheme.colorScheme.background`/`surface`/`surfaceVariant` tokens (already defined, no new colors) across Hero/Skills/Experience/Projects/Contact so consecutive sections are visually distinguishable.

## Capabilities

### New Capabilities
- (none — this extends existing capabilities' requirements, not new ones)

### Modified Capabilities
- `hero-about-section`: the avatar requirement's "photo" branch goes from theoretical to implemented — add a scenario covering the photo-present case.
- `design-system`: adds a new requirement that consecutive portfolio sections use alternating theme background/surface tokens, so section boundaries are visually distinguishable.

## Impact

- `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/sections/HeroAboutSection.kt`: `Avatar()` gains a photo-or-initials branch.
- `shared/src/commonMain/composeResources/`: new directory, holds the headshot image asset (Ciro needs to supply the actual photo file).
- Each section file (`HeroAboutSection.kt`, `SkillsSection.kt`, `ExperienceSection.kt`, `ProjectsSection.kt`, `ContactSection.kt`) or `PortfolioShell.kt`: apply an alternating background/surface token per section — implementation approach (per-section vs shell-driven) is a design decision.
