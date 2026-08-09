## Why

There is no automated test coverage anywhere in the project — no `test/` source set in any module. `AGENTS.md` already documents `./gradlew test` as a project command, but today it has nothing meaningful to run. As more cross-cutting changes (i18n, CV export) land in parallel, regressions in shared rendering logic (section registry, shell composition, per-section content) would go unnoticed until manual verification.

## What Changes

- Add a `commonTest` source set to `:shared` with `kotlin.test` for pure logic and Compose Multiplatform's `runComposeUiTest` for composable smoke tests.
- Add a registry test for `PortfolioSection` (correct count, order, non-empty titles).
- Add a smoke test for `PortfolioShell` (renders every declared section's content).
- Add a smoke test per existing section (`HeroAboutSection`, `SkillsSection`, `ExperienceSection`, `ProjectsSection`, `ContactSection`) confirming it renders without throwing and exposes its expected structural content.
- Document the testing approach and how to run it, so future changes (e.g. i18n, CV export) can add their own tests against the same conventions.

## Capabilities

### New Capabilities
- `shared-test-coverage`: automated test coverage for `:shared`'s registry, shell, and section composables, runnable via `./gradlew test`.

### Modified Capabilities
- (none — this change adds verification for existing behavior; it does not change any section's or the shell's requirements)

## Impact

- `shared/src/commonTest/kotlin/app/luxion/nexus/` — new test source set
- `shared/build.gradle.kts` — test dependencies (`kotlin.test`, Compose UI testing artifacts) if not already present
- No production code changes to `:shared`, `:webApp`, `:androidApp`, `:iosApp`, `:desktopApp`
