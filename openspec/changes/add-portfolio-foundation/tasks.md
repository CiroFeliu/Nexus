## 1. Remove wizard scaffold

- [ ] 1.1 Delete `Greeting.kt`, `GreetingUtil.kt`, and the sample `compose-multiplatform` drawable/resource from `:shared/src/commonMain`
- [ ] 1.2 Remove references to the deleted files from `App.kt` and any platform `Platform.*.kt` files that only existed to back the demo

## 2. Design system

- [ ] 2.1 Add `theme/Color.kt` with light and dark `ColorScheme` definitions (clean/technical/modern tone)
- [ ] 2.2 Add `theme/Typography.kt` with the Material3 typography scale used across the portfolio
- [ ] 2.3 Add `theme/Shape.kt` / spacing tokens for consistent corner radii and padding
- [ ] 2.4 Add `theme/PortfolioTheme.kt`: a `@Composable` wrapper that picks light/dark scheme via `isSystemInDarkTheme()` and applies color/typography/shape tokens through `MaterialTheme`
- [ ] 2.5 Verify `MaterialTheme.colorScheme`/`typography` resolve to the same values by building `:webApp` and `:desktopApp` locally

## 3. Navigation shell

- [ ] 3.1 Add a shared `SectionPlaceholder(title: String)` composable (title + "TODO" marker) under `navigation/`
- [ ] 3.2 Add one file per section under `navigation/sections/`: `HeroAboutSection.kt`, `SkillsSection.kt`, `ExperienceSection.kt`, `ProjectsSection.kt`, `ContactSection.kt` — each exposing a small object with a title and a `Content()` composable that calls `SectionPlaceholder(title)` by default. Follow-up section changes will only edit their own file's `Content()` body.
- [ ] 3.3 Add `navigation/PortfolioSection.kt`: an ordered registry (list/sealed dispatch) referencing the five section objects from 3.2, in display order
- [ ] 3.4 Add `navigation/PortfolioShell.kt`: a `@Composable` that iterates the registry and renders each section's `Content()` in a single scrollable column
- [ ] 3.5 Rewrite `App.kt` to wrap `PortfolioShell()` in `PortfolioTheme { }`, removing the old "Click me!" demo body

## 4. External link opening

- [ ] 4.1 Add `expect fun openUrl(url: String)` alongside the existing `Platform.kt` declarations in `:shared/src/commonMain`
- [ ] 4.2 Implement `actual fun openUrl` in `androidMain` (`Intent(Intent.ACTION_VIEW, ...)`), `jsMain`/`wasmJsMain` (`window.open`), `jvmMain` (`Desktop.getDesktop().browse`), and `iosMain` (`UIApplication.sharedApplication.openURL`)

## 5. Cross-target verification

- [ ] 5.1 Run `./gradlew :webApp:wasmJsBrowserDevelopmentRun` and confirm the shell renders with all 5 placeholder sections in both light and dark OS mode
- [ ] 5.2 Run `./gradlew :desktopApp:run` and confirm parity with the web output
- [ ] 5.3 Run `./gradlew :androidApp:assembleDebug` and confirm it builds (manual device/emulator check optional at this stage)
- [ ] 5.4 Confirm `:iosApp` still compiles against the updated `:shared` (Xcode build or `./gradlew :shared:linkDebugFrameworkIosSimulatorArm64`)
- [ ] 5.5 Run `./gradlew test` to confirm no shared module tests were broken by removing the wizard scaffold
