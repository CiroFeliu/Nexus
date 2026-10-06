## Context

`PortfolioShell` currently lays out nav + language rows above a `verticalScroll` column. For a floating bar the scroll container must fill the window and the bar must draw on top, with section offsets accounting for the bar height when scrolling to a section.

This is the **overhaul** variant; the **evolve** variant lives on `feature/improve-design-evolve`. Section ids, order and nav labels stay unchanged.

## Goals / Non-Goals

**Goals:**
- A hero that is unmistakably designed, not templated, at every width.
- Navigation that stays out of the way over the hero and is always reachable.

**Non-Goals:**
- Logo/monogram artwork (the wordmark is set text).
- Backdrop blur (no Wasm-proven library in the project; solid surface instead).
- Implementing animations (owned by `add-motion-layer`).

## Decisions

- **Shell layering.** `Box(fillMaxSize)`: scroll column first, `PortfolioTopBar` second, aligned top. Section jump target = section offset minus bar height. The hero adds top padding equal to the bar height so nothing hides behind it.
- **Bar states.** `isScrolled` derived from `scrollState.value > 0` via `derivedStateOf`. Top: transparent, no hairline. Scrolled: `surfaceContainer` + `outlineVariant` hairline. Height 64dp. Color transition uses motion tokens.
- **Compact menu.** Full-screen overlay (`Popup`/top-level `Box` layer) with section links in `headlineMedium`, language toggle and close button; `Esc` and back close it; focus moves into it when opened.
- **Hero grid.** Expanded+: `Row` with text column `weight(7f)` and portrait column `weight(5f)`. The portrait column is pushed down with `Modifier.offset(y = 48.dp)` and the hero section disables clipping so the image overlaps the next section's top edge; the next section reserves that space in its top padding. Medium: same split 6/6, smaller offset. Compact: portrait (4:5, full width up to 320dp) below the actions, no overlap.
- **Hero height.** `BoxWithConstraints`: `heightIn(min = maxHeight * 0.85f)` on medium+, content vertically centered; never a fixed dp height. Verified at 1280x720 and 1920x1080.
- **Hero type.** Name: a dedicated `heroDisplay` style (Geist 600, ~96sp at large, ~72sp expanded, ~48sp compact, tracking -0.04em, line height 1.0) split into first/last name lines by layout, not by `\n` in content. Role: Geist Mono `labelLarge` in `primary`. Bio: `bodyLarge`, `readableTextWidth`.
- **Grain.** A small noise `ImageBitmap` generated once at startup (deterministic seed) and tiled with `drawWithCache` in a full-window overlay *outside* the scroll container, alpha ~0.04 light / ~0.06 dark, transparent to pointer input. It is static, so it stays on under reduced motion.
- **Photo treatment.** `shapes.large`, `ContentScale.Crop`, reserved 4:5 space; optional duotone is out of scope (keep the real photo honest).

## Risks / Trade-offs

- [Risk] Overlapping portrait across a section boundary complicates section offset math and scroll-to-section accuracy. → The overlap is purely visual (`offset` does not affect measured size); offsets stay correct.
- [Risk] Full-window grain overlay costs a draw per frame. → Cached tile, drawn once per frame without allocations; verify 60fps scroll on web.
- [Risk] Very large display type in Spanish may wrap to 3 lines on medium widths. → Name is the only display text and is language-independent.

## Open Questions

- A higher-resolution portrait (current asset is 576x576, square; the 4:5 crop at ~360dp wide on 2x screens wants ~720x900).
- Final 20-word bio (EN/ES).
