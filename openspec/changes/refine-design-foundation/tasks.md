## 1. Typeface

- [x] 1.1 Add Geist (400/500/600/700) and Geist Mono (400/500) files under `shared/src/commonMain/composeResources/font/`, plus their SIL OFL license text in the repo
- [x] 1.2 Build `FontFamily` values for Geist and Geist Mono in the theme and apply Geist to all 15 `Typography` styles with tuned tracking/line heights
- [x] 1.3 Expose Geist Mono text styles (date, tag, technical label) as theme tokens through a `CompositionLocal`
- [x] 1.4 Preload the fonts in the web entry and keep the HTML loader until they resolve
- [x] 1.5 Measure the production Wasm distribution size before/after; stay within ~350 KB added

## 2. Color schemes

- [x] 2.1 Define one cool-neutral gray family and map it to every surface/outline/inverse role in light and dark
- [x] 2.2 Tune the blue accent family (`primary`, containers, `tertiary`, `inversePrimary`, `surfaceTint`) for AA contrast in both schemes
- [x] 2.3 Verify no `ColorScheme` role keeps a Material baseline default (unit test comparing against `lightColorScheme()`/`darkColorScheme()` defaults)

## 3. Tokens and helpers

- [x] 3.1 Add `PortfolioLayout` (max content width, readable text width, breakpoints, gutters, section vertical padding) and `PortfolioContentContainer`
- [x] 3.2 Add motion tokens (durations, easing, springs) that collapse to instant when reduced motion is on
- [x] 3.3 Document the shape rule in `Shape.kt` naming (no comments) and stop using `extraLarge` for tags
- [x] 3.4 Add `Modifier.interactive(...)` (hand cursor, hover, pressed scale, focus outline)

## 4. Reduced motion

- [x] 4.1 Add `expect` reduced-motion reader in `commonMain` and actuals for wasmJs, js, android, ios, jvm
- [x] 4.2 Provide `LocalReducedMotion` from `PortfolioTheme`

## 5. Verification

- [x] 5.1 Run `./gradlew test`
- [ ] 5.2 Run the web app in light and dark at 360/768/1280/1920 wide; check no fallback font flash, no purple cast on cards/chips, pointer cursor on every clickable
- [ ] 5.3 Run `:desktopApp:run` and build `:androidApp:assembleDebug` to confirm fonts and theme on non-web targets
- [x] 5.4 `openspec validate refine-design-foundation --strict`
