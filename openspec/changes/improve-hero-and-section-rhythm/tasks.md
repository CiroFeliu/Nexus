## 1. Theme tokens

- [x] 1.1 Give `surface` a subtly distinct value from `background` in both `PortfolioLightColorScheme` and `PortfolioDarkColorScheme` (`Color.kt`) — small delta, keep both readable/on-brand
- [x] 1.2 Verify existing composables that already read `surface`/`background` (cards, dividers) still look correct with the new values

## 2. Section rhythm

- [x] 2.1 Decide alternation order across the five `PortfolioSection` entries (e.g. background/surface/background/surface/background) and apply it from `PortfolioShell` rather than per-section files
- [x] 2.2 Verify light and dark mode both read correctly at every section boundary

## 3. Hero photo support

- [x] 3.1 Create `shared/src/commonMain/composeResources/drawable/` and add the headshot asset (blocked on Ciro supplying the photo file)
- [x] 3.2 Update `HeroAboutSection.Avatar()` to accept an optional photo painter, falling back to initials when absent
- [x] 3.3 Wire the photo resource in via `painterResource`, sized/clipped consistently with the current circular initials avatar

## 4. Tests & verification

- [x] 4.1 Add/extend `commonTest` coverage for the avatar fallback logic (photo present vs absent)
- [x] 4.2 Manually verify on `:webApp` in both light and dark system preference
- [x] 4.3 Run `./gradlew test` for all modules

## 5. Spec housekeeping

- [x] 5.1 Confirm `openspec validate improve-hero-and-section-rhythm --strict` passes before archiving
