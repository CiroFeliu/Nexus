## 1. Content

- [ ] 1.1 Define `ExperienceEntry` data class and a constant list of role entries (most-recent-first) in `ExperienceSection.kt`
- [ ] 1.2 Implement a single timeline-entry composable (connector line/dot + company/title/dates/description)
- [ ] 1.3 Implement `ExperienceSection.Content()` rendering all entries in a vertical `Column` using `PortfolioTheme` tokens

## 2. Verification

- [ ] 2.1 Run `./gradlew :webApp:wasmJsBrowserDevelopmentRun` and confirm the timeline renders in order with correct styling in both light and dark mode
- [ ] 2.2 Confirm no other files (`PortfolioShell.kt`, `PortfolioSection.kt`, other sections) were modified
