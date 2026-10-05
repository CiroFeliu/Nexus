# shared-test-coverage Specification

## Purpose
Give `:shared` automated regression coverage — the section registry, `PortfolioShell` composition, and each section's `Content()` composable — runnable via the project's documented `./gradlew test` command with no extra setup, so cross-cutting changes (i18n, CV export, etc.) landing in parallel don't silently break shared rendering logic.
## Requirements
### Requirement: Section registry is tested
The system SHALL include a test verifying `PortfolioSection.entries` contains exactly the declared sections, in the declared order, each with a non-empty title.

#### Scenario: Registry test runs
- **WHEN** `./gradlew test` runs the `:shared` test suite
- **THEN** the `PortfolioSection` registry test passes if and only if the entries match the declared sections, order, and non-empty titles

### Requirement: Shell composition is tested
The system SHALL include a smoke test verifying `PortfolioShell` renders every declared section's content when composed.

#### Scenario: Shell smoke test runs
- **WHEN** `./gradlew test` runs the `:shared` test suite
- **THEN** the `PortfolioShell` test passes if and only if every section declared in `PortfolioSection` produced rendered output

### Requirement: Each section has a smoke test
The system SHALL include a smoke test per section (`HeroAboutSection`, `SkillsSection`, `ExperienceSection`, `ProjectsSection`, `ContactSection`) verifying its `Content()` composable renders without throwing and exposes its expected structural content.

#### Scenario: Section smoke test runs
- **WHEN** `./gradlew test` runs the `:shared` test suite
- **THEN** each section's smoke test passes if and only if that section's `Content()` renders without throwing and its expected structural elements are present

### Requirement: Tests run via the standard project command
The system SHALL make all `:shared` tests runnable via the project's documented `./gradlew test` command, with no additional setup required.

#### Scenario: Fresh checkout runs tests
- **WHEN** a contributor runs `./gradlew test` on a fresh checkout of the repository
- **THEN** the `:shared` test suite executes and reports pass/fail without additional configuration steps

