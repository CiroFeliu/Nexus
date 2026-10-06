## Context

Sections are isolated files rendered by `PortfolioShell`, which today wraps each in an alternating `Surface`. The `design-system` spec requires that alternation (added by the archived `improve-hero-and-section-rhythm` change). This is the **overhaul** variant; layouts here intentionally differ from the evolve branch so the two can be compared side by side.

## Goals / Non-Goals

**Goals:**
- Each section a different layout family: bento, index list, editorial rows, column lists, statement block.
- Real visuals in Projects, or explicit, well-designed fallbacks while assets are missing.
- No chips except the filter control.

**Non-Goals:**
- Animations (owned by `add-motion-layer`; layouts must look complete without motion).
- New content or projects.
- Detail pages per project.

## Decisions

- **Single background.** Shell renders sections directly on `background`; between sections a full-width `outlineVariant` hairline inside the content container. Section padding from `PortfolioLayout` (larger than evolve: density 3).
- **Bento.** Custom `Layout` (not `LazyVerticalStaggeredGrid`, the grid is small and lives in a non-lazy column) placing exactly `featuredProjects.size` tiles. Expanded+ (6 items): row 1 = large tile (2/3 width, tall) + tall tile (1/3); row 2 = three equal tiles; row 3 = one wide tile. Medium: 2 columns with one full-width tile. Compact: single column. Placement is a pure function from item count + width class to spans, unit tested so the cell count always equals the item count.
- **Tile visuals.** `ProjectVisual` per project: `DrawableResource?` image (screenshot or photo, `ContentScale.Crop`, reserved aspect) or fallback: tinted `primaryContainer`/`surfaceContainerHigh` surface with the project name in `displaySmall` and the company in mono. At least two tiles must use a visual treatment different from plain surface (real images, or accent-tinted fallbacks). Text sits below the visual, never as pills overlaid on images.
- **Index list.** Rows: name (`titleMedium`), company (mono), tags (mono, comma-separated, hidden on compact), trailing open-in-new icon button with descriptive `contentDescription`. Group framed by one top and one bottom hairline, rows separated by spacing only. Filter chips derived from non-featured projects.
- **Experience rows.** Medium+: company in `headlineLarge` (left, ~40%), role `titleMedium` + description `bodyLarge` + mono dates (right). Compact: stacked. Thin 1dp rail on the left at compact only.
- **Skills.** `Row` of four `Column`s (expanded+), two (medium), one (compact); category label `titleMedium`, items in mono `bodyMedium`, line height generous. Optional core-stack line with `Modifier.basicMarquee` (disabled under reduced motion, static wrap instead).
- **Contact block.** Headline statement (`displaySmall`), email as `headlineMedium` link (`mailto`) inside `SelectionContainer` + copy button, brand links, filled "Download CV", then the footer content rendered by the shell in the same visual block (spec still renders the footer once after the last section).
- **Icons and marks.** Same set as the foundation-level decision: Material Symbols Rounded vectors, official GitHub/LinkedIn marks; no `material-icons-extended`.

## Risks / Trade-offs

- [Risk] Project screenshots are not in the repo; NDA projects cannot have them. → Fallback tiles are designed, not placeholders; tasks list exactly which images to request.
- [Risk] Removing the alternation reverses a previously archived decision. → Explicit `MODIFIED` delta on `design-system` with the reason (bands read as template; separators + spacing carry the rhythm).
- [Risk] Custom bento `Layout` complexity. → Pure span function tested separately; the `Layout` only measures/places.
- [Trade-off] Chips removed from skills reduce "tag cloud" scannability; mono lists read more like a spec sheet, matching the technical tone.

## Open Questions

- Which project images Ciro can provide: Nexus (screenshot of this site), ShogunAi (desktop screenshot), DuoxMe and HCB Paciente (store screenshots), Revieve (public product image), Zenith (drone photo).
- Keep or drop the core-stack marquee after seeing it.
