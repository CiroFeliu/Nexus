## 1. Content

- [ ] 1.1 Define `Project` data class and a constant list of featured projects (including Nexus) in `ProjectsSection.kt`
- [ ] 1.2 Implement a project-card composable (title, description, tech tags, repo/demo link button wired to `openUrl`)
- [ ] 1.3 Implement `ProjectsSection.Content()` rendering all project cards in a responsive grid using `PortfolioTheme` tokens

## 2. Verification

- [ ] 2.1 Run `./gradlew :webApp:wasmJsBrowserDevelopmentRun` and confirm a project link opens a new browser tab
- [ ] 2.2 Run `./gradlew :desktopApp:run` and confirm a project link opens the default browser
- [ ] 2.3 Confirm no other section files (`PortfolioShell.kt`, `PortfolioSection.kt`, other sections) were modified
