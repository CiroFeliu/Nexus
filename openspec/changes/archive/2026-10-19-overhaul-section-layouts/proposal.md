## Why

Variant **overhaul** (`feature/improve-design-overhaul`, dials 7/6/3). The sections below the hero repeat the same generic shapes: zebra-striped full-width bands, centered header in Projects, featured projects rendered twice as identical 320dp elevated cards with 11 filled "View project" buttons, a hand-drawn link icon, chips everywhere (skills, tags, filters), and a contact row of four identical outlined buttons. Nothing is visual except the avatar.

## What Changes

- **One page background.** Drop the alternating section bands; sections are separated by generous spacing and one full-width hairline. Every section opens with a large left-aligned headline (no eyebrows, no numbering).
- **Projects:** the six featured projects become a **bento grid** with rhythm (one large tile, mixed spans, exact cell count), each tile with real visual variation: a project screenshot/photo when supplied, otherwise a tinted accent surface with the project name set large. The remaining projects become an **index list** (name, company, tags, link) with the filter chips above it. Real "open in new" icon, links instead of filled buttons.
- **Experience:** each role as an editorial row: company name in large display type, role and description beside it, dates in Geist Mono; a thin rail replaces dots.
- **Skills:** four category columns (two on medium, one on compact) as plain mono lists instead of chips; one optional marquee line of the core stack, the only marquee on the page.
- **Contact:** closing statement as a large headline, the email as a display-size selectable link with copy action, LinkedIn/GitHub with brand marks, single filled "Download CV"; the footer sits inside the same block.

## Capabilities

### New Capabilities
- (none)

### Modified Capabilities
- `design-system`: replaces the alternating section background requirement with a single page background and separators.
- `projects-section`: featured projects as a bento grid with visuals; remaining projects as an index list.
- `project-filtering`: filters apply to the index list of non-featured projects, with an empty state.
- `skills-section`: skills render as grouped lists instead of chips; reflow requirement updated accordingly.

## Impact

- `navigation/PortfolioShell.kt`, every file in `navigation/sections/`, `navigation/PortfolioFooter.kt`
- New drawables (icons, brand marks); project visuals start as designed placeholders (no project images yet), with an optional image slot per project for later
- Tests: projects split/filter scope/empty state, bento cell count equals featured count, section smoke tests
- Builds on the archived foundation, copy and web-shell changes and on `overhaul-hero-and-navigation`; motion in `add-motion-layer`
