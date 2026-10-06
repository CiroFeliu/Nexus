## Why

Variant **evolve** (`feature/improve-design-evolve`, dials 5/4/4). After the shared foundation, the sections still carry generic patterns:

- **Projects** renders the six featured projects twice (Featured row, then again inside "All"), as fixed 320dp `ElevatedCard`s with default elevation, an uppercase company eyebrow on every card, an internal divider and a full-width filled "View project" button on 11 cards. The external-link icon is hand-drawn with `Canvas`. The section header is centered while the rest of the page is left-aligned.
- **Experience** stretches to full window width and stacks role, company, dates and description with equal weight.
- **Skills** is four stacked label + chip rows; fine content, flat structure.
- **Contact** is a row of four identical outlined buttons plus the email in body text.
- Every section uses the same 24dp padding; the alternating section backgrounds rely on `background` vs `surface` only.

## What Changes

- **Projects:** Featured projects render once, as a 2-column grid (1 column compact) of flat tonal cards (`surfaceContainer`, no elevation, `large` shape). Remaining projects render as a compact list (name, company in mono, tags, link) under the filter chips, a different layout family from the cards. Links become text links with a real "open in new" icon from the Material Symbols set (vector drawable resource). Company label in Geist Mono sentence case. Left-aligned section header.
- **Experience:** two-column rows at medium+ (date range in Geist Mono on a fixed left column, role/company/description on the right), timeline line kept; compact stacks dates above the role.
- **Skills:** 2x2 grid of category blocks at expanded widths, stacked otherwise; chips restyled with the shape rule (`small`, tonal).
- **Contact:** heading, the email as a large selectable text with a copy action, LinkedIn/GitHub as text links with official brand marks, "Download CV" as the single filled button.
- **Rhythm:** all sections inside `PortfolioContentContainer` with breakpoint section padding; alternation uses `background` and `surfaceContainerLow` (subtle tonal shift, same scheme). Footer left-aligned with a top hairline.

## Capabilities

### New Capabilities
- (none)

### Modified Capabilities
- `projects-section`: featured projects render as cards; the remaining catalogue renders as a compact list.
- `project-filtering`: filters apply to the remaining (non-featured) catalogue, with an empty state.

## Impact

- `navigation/sections/ProjectsSection.kt`, `ExperienceSection.kt`, `SkillsSection.kt`, `ContactSection.kt`, `navigation/PortfolioFooter.kt`, `navigation/PortfolioShell.kt` (container + rhythm tokens)
- New vector drawables in `composeResources/drawable/` (open-in-new, copy, GitHub mark, LinkedIn mark)
- Tests: `ProjectsSection` filter logic (non-featured scope, empty state), section smoke tests
- Builds on the archived foundation and copy changes
