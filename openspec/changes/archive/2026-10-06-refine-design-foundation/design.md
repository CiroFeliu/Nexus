## Context

Theme lives in `shared/.../theme/`: `PortfolioTheme` wraps `MaterialTheme` with `PortfolioLightColorScheme`/`PortfolioDarkColorScheme`, `PortfolioTypography` (8 of 15 styles, no font family) and `PortfolioShapes` + `PortfolioSpacing`. Sections read `MaterialTheme.*` correctly (no literal colors in composables), so improving the tokens propagates everywhere without touching section files.

This change is the shared base for two comparison branches (`feature/improve-design-evolve`, `feature/improve-design-overhaul`). Anything both variants need lives here; anything that is a layout or motion choice does not.

## Goals / Non-Goals

**Goals:**
- No text on any target renders in a fallback/default font.
- No M3 color role resolves to a baseline default.
- One place defines spacing, widths, motion timings and interactive feedback.
- Reduced-motion preference is readable from any composable.

**Non-Goals:**
- Section layout changes, hero redesign, new components (variant changes).
- Copy changes (`polish-portfolio-copy`).
- `index.html` metadata (`improve-web-shell`), except the loader/font-readiness handshake below.
- A manual light/dark toggle. System preference stays the only source.

## Decisions

- **Typeface: Geist + Geist Mono, static weights.** Geist reads technical and modern without being Inter/Roboto; Geist Mono gives dates, tech tags and small labels a consistent "engineering" texture. Ship static TTF/OTF files rather than the variable font: `org.jetbrains.compose.resources.Font` supports `variationSettings`, but static files are the lowest-risk path on Skia/Wasm and let us ship only the weights used. Planned set: Geist 400/500/600/700, Geist Mono 400/500 (~6 files). If the total exceeds the ~350 KB budget, drop Geist 700 and use 600 for display.
- **Typography scale (Geist).** Display styles use negative tracking (`-0.03em` large, `-0.02em` medium/small) and line height ~1.05-1.1x. Headlines 600 weight, titles 500/600, body 400 with tracking `0` (not M3's `0.5sp`) and line height ~1.55x. Labels use Geist 500; a separate `PortfolioTextStyles.mono*` set exposes Geist Mono styles for dates/tags (M3 `Typography` has no mono slot, so these live next to it as theme-level tokens provided through a `CompositionLocal`).
- **Color: one neutral family + refined blue.** Neutrals are a cool gray lightly tinted toward the accent hue, used for `background`, `surface`, all five `surfaceContainer*` roles, `surfaceDim/Bright`, `outline`, `outlineVariant`, `inverseSurface`, `inverseOnSurface`. Accent stays in the `#2F6FED` family (tuned for AA on both schemes); `tertiary` maps to the same accent family instead of an unused third hue; `inversePrimary`, `scrim`, `surfaceTint` set explicitly. Off-black / off-white only. Exact hex values are chosen during implementation and verified with a contrast check (AA body, AA large for labels) in both schemes.
- **Shapes: soft system.** Keep 4/8/12/16 with one documented rule: interactive elements (buttons, chips) use `small`/`medium`; containers use `large`; `extraLarge` (28dp) is not used for tags anymore (today `TechTag` uses it, producing pills next to 16dp cards).
- **Layout tokens.** `PortfolioLayout.maxContentWidth = 1200.dp`, `readableTextWidth = 640.dp`, breakpoints compact < 600dp, medium < 840dp, expanded < 1200dp, large otherwise, plus `sectionPaddingVertical` per breakpoint. A `PortfolioContentContainer` composable centers content and applies the cap and horizontal gutters (16dp compact, 24dp medium, 32dp+ expanded).
- **Interaction modifier.** `Modifier.interactive(interactionSource)` applies `pointerHoverIcon(PointerIcon.Hand)`, hover feedback (subtle background/translation driven by `collectIsHoveredAsState`), pressed scale 0.98 in `graphicsLayer`, and a focus outline when `collectIsFocusedAsState` is true. Animated values are read inside `graphicsLayer`/draw lambdas, so hover never triggers recomposition of children. Material buttons/chips keep their own state layers; the modifier adds cursor + press scale + focus ring only where they are missing.
- **Reduced motion.** `@Composable expect fun rememberReducedMotionPreference(): Boolean` in `commonMain`, provided once in `PortfolioTheme` as `LocalReducedMotion`. Actuals: Wasm/JS `window.matchMedia("(prefers-reduced-motion: reduce)").matches`; Android `Settings.Global.ANIMATOR_DURATION_SCALE == 0f`; iOS `UIAccessibilityIsReduceMotionEnabled()`; desktop `false`. Motion tokens expose helpers that collapse to `snap()` when the local is true.
- **Font readiness on web.** Use compose resources' `preloadFont` (available in the project's CMP 1.11) for the Geist files in the Wasm/JS entry, and only remove the HTML loader once they resolve. Other targets load fonts synchronously from bundled resources.

## Risks / Trade-offs

- [Risk] Font files enlarge the first Wasm download. → Budget ~350 KB, static weights only, measure the production bundle before and after.
- [Risk] Skia text shaping on Wasm can differ slightly from Android for negative tracking. → Verify display styles on the web target first; keep tracking modest.
- [Risk] `preloadFont` is marked experimental. → Isolated to the web entry; fallback is the current behaviour (render immediately).
- [Trade-off] Defining every color role by hand is more code than seeding a scheme, but seeded schemes are exactly the generic M3 look this change removes.

## Open Questions

- Final accent hex after contrast tuning (stay at `#2F6FED` or shift slightly cooler/darker for AA on `surfaceContainer`).
