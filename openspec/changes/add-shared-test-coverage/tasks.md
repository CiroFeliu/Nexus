## 1. Test infrastructure

- [ ] 1.1 Add Compose Multiplatform UI testing dependency (`runComposeUiTest` artifact) to `commonTest` in `shared/build.gradle.kts`
- [ ] 1.2 Create `shared/src/commonTest/kotlin/app/luxion/nexus/` directory structure mirroring `commonMain`

## 2. Registry and shell tests

- [ ] 2.1 `PortfolioSectionTest.kt` — assert entry count, order, and non-empty titles
- [ ] 2.2 `PortfolioShellTest.kt` — assert every declared section's content is rendered when the shell is composed

## 3. Per-section smoke tests

- [ ] 3.1 `HeroAboutSectionTest.kt` — renders without throwing; name, role, bio, and avatar are present
- [ ] 3.2 `SkillsSectionTest.kt` — renders without throwing; expected number of category groups are present
- [ ] 3.3 `ExperienceSectionTest.kt` — renders without throwing; expected number of timeline entries are present
- [ ] 3.4 `ProjectsSectionTest.kt` — renders without throwing; expected number of project cards are present
- [ ] 3.5 `ContactSectionTest.kt` — renders without throwing; expected contact links are present

## 4. Verification

- [ ] 4.1 Run `./gradlew test` on a fresh checkout and confirm all new tests pass with no additional setup
