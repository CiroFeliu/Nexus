## Why

The portfolio still reads as an unstyled Material 3 app. `PortfolioTypography` sets no `fontFamily`, so every target (and especially Wasm, which has no system fonts) renders the engine default, and every text style the file does not declare falls back to stock M3 values. `Color.kt` defines only ~17 roles: `surfaceContainer*`, `outlineVariant`, `tertiary`, `inverse*` and friends silently keep the M3 baseline (purple-tinted) values, which is why `ElevatedCard` and `FilterChip` render with a faint purple cast against the neutral page. On web nothing shows a pointer cursor, there is no shared pressed/focus feedback, no content width limit outside Projects, and no way to honour a visitor's reduced-motion preference.

This change gives both upcoming redesign variants (evolve and overhaul) one shared, deliberate foundation so they differ only in layout and motion, not in tokens.

## What Changes

- Bundle **Geist** (UI/display) and **Geist Mono** (dates, tech tags, small technical labels) in `composeResources/font` and wire them into a complete `Typography` (all 15 M3 styles), with tuned tracking and line heights.
- Rebuild both color schemes so **every** M3 role is defined from one cool-neutral gray family plus the existing blue accent (refined, not replaced). No role keeps a baseline default.
- Extend tokens: spacing scale (adds section-level spacing), layout tokens (`maxContentWidth`, `readableTextWidth`, width breakpoints), motion tokens (durations, standard easing/springs).
- Add a shared `Modifier` for interactive elements on all targets: pointer hand cursor, hover feedback, pressed scale, visible keyboard focus.
- Add a `LocalReducedMotion` composition local backed by an `expect`/`actual` platform reading (`prefers-reduced-motion` on Wasm/JS, animator scale on Android, `UIAccessibilityIsReduceMotionEnabled` on iOS, `false` on desktop).
- Add a shared centered content container composable so every section can cap its width consistently.
- On web, hold the HTML loading screen until the bundled fonts are ready so the first frame never renders with fallback glyphs.

## Capabilities

### New Capabilities
- (none)

### Modified Capabilities
- `design-system`: adds requirements for a bundled typeface, a fully-specified color scheme, a capped content width, consistent interactive states, and a reduced-motion preference.

## Impact

- `shared/src/commonMain/kotlin/app/luxion/nexus/theme/` (`Color.kt`, `Typography.kt`, `Shape.kt`, `PortfolioTheme.kt`, new `Motion.kt`, `Layout.kt`, `Interaction.kt`)
- New `expect`/`actual` `ReducedMotion` in `commonMain` + `wasmJsMain`, `jsMain`, `androidMain`, `iosMain`, `jvmMain`
- `shared/src/commonMain/composeResources/font/` (new, Geist + Geist Mono, SIL OFL 1.1)
- Wasm download grows by the font files (budget: <= ~350 KB total)
- Sections are not restyled here beyond picking up the new tokens automatically; layout work belongs to the variant changes.
