## Context

Sections are isolated files rendered by `PortfolioShell`, which today wraps each in an alternating `Surface`. The `design-system` spec requires that alternation (added by the archived `improve-hero-and-section-rhythm` change). This is the **overhaul** variant; layouts here intentionally differ from the evolve branch so the two can be compared side by side.

## Foundation available

Built on the archived `refine-design-foundation`, `polish-portfolio-copy` and `improve-web-shell` changes. Use their pieces instead of re-creating them: `PortfolioContentContainer`, `LocalWidthClass`/`WidthClass`, `PortfolioLayout`, `PortfolioMotion` + `motionSpec()`, `LocalReducedMotion`, `Modifier.interactive(...)`, `LocalPortfolioMonoTypography`, `PortfolioShapeRoles`, and the shell's `notifyAppReady()`/`applyDocumentLanguage()` bridge.

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
- **Tile visuals (placeholders for now).** `ProjectVisual` per project with an optional `DrawableResource?` image slot that stays `null` for every project in this change. The placeholder is a designed visual, not a grey box: a tinted surface (alternating `primaryContainer`, `tertiaryContainer` and `surfaceContainerHigh` so neighbouring tiles differ), the project name set large in `displaySmall`, the company in mono, and a subtle geometric texture drawn with `drawWithCache` (fine grid or diagonal hatching in `outlineVariant` at low alpha). When real screenshots exist later, setting the image slot swaps the placeholder for the image (`ContentScale.Crop`, reserved aspect) with no layout change. Text sits below the visual, never as pills overlaid on images.
- **Index list.** Rows: name (`titleMedium`), company (mono), tags (mono, comma-separated, hidden on compact), trailing open-in-new icon button with descriptive `contentDescription`. Group framed by one top and one bottom hairline, rows separated by spacing only. Filter chips derived from non-featured projects.
- **Experience rows.** Medium+: company in `headlineLarge` (left, ~40%), role `titleMedium` + description `bodyLarge` + mono dates (right). Compact: stacked. Thin 1dp rail on the left at compact only.
- **Skills.** `Row` of four `Column`s (expanded+), two (medium), one (compact); category label `titleMedium`, items in mono `bodyMedium`, line height generous. Optional core-stack line with `Modifier.basicMarquee` (disabled under reduced motion, static wrap instead).
- **Contact block.** Headline statement (`displaySmall`), email as `headlineMedium` link (`mailto`) inside `SelectionContainer` + copy button, brand links, filled "Download CV", then the footer content rendered by the shell in the same visual block (spec still renders the footer once after the last section).
- **Icons and marks.** Same set as the foundation-level decision: Material Symbols Rounded vectors, official GitHub/LinkedIn marks; no `material-icons-extended`.

## Risks / Trade-offs

- [Risk] Project screenshots are not in the repo; NDA projects cannot have them. → Placeholders are designed tiles (tint, type, texture), and the image slot makes adding real images later a data-only change.
- [Risk] Removing the alternation reverses a previously archived decision. → Explicit `MODIFIED` delta on `design-system` with the reason (bands read as template; separators + spacing carry the rhythm).
- [Risk] Custom bento `Layout` complexity. → Pure span function tested separately; the `Layout` only measures/places.
- [Trade-off] Chips removed from skills reduce "tag cloud" scannability; mono lists read more like a spec sheet, matching the technical tone.

## Open Questions

- Real project images (Nexus, ShogunAi, DuoxMe, HCB Paciente, Revieve, Zenith) can replace the placeholders later through the image slot.
- Keep or drop the core-stack marquee after seeing it.
