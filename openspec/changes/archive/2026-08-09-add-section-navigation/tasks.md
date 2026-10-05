## 1. Section identity plumbing

- [x] 1.1 Confirm `PortfolioSection` already exposes a per-language display label; add one if it doesn't
- [x] 1.2 Add a mechanism for the shell to record each section's on-screen offset as it renders (e.g. `Modifier.onGloballyPositioned` wrapped around each section from the shell, not from within each section file)

## 2. Navigation control

- [x] 2.1 Create the section-navigation composable (new file under `navigation/`) rendering one link per `PortfolioSection`
- [x] 2.2 Wire click handling to `ScrollState.animateScrollTo` using the recorded section offsets
- [x] 2.3 Derive and expose the active section from current `ScrollState.value` vs recorded offsets
- [x] 2.4 Style the active link distinctly using `PortfolioTheme` colors/typography only

## 3. Shell integration

- [x] 3.1 Place the new nav control alongside `LanguageSwitcher` in `PortfolioShell`, outside the scrollable `Column`
- [x] 3.2 Verify layout on narrow widths (nav + language switcher coexist without overlap/clipping)

## 4. Tests & verification

- [x] 4.1 Add/extend `commonTest` coverage for the section-navigation composable (link count matches section count, active-section derivation logic)
- [x] 4.2 Manually verify on `:webApp` (wasmJsBrowserDevelopmentRun): clicking each link scrolls to the right section, active link updates while scrolling
- [x] 4.3 Run `./gradlew test` for all modules

## 5. Spec housekeeping

- [x] 5.1 Confirm `openspec validate add-section-navigation --strict` passes before archiving
