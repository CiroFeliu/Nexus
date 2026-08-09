# navigation-shell Specification

## Purpose
TBD - created by archiving change add-portfolio-foundation. Update Purpose after archive.
## Requirements
### Requirement: Portfolio sections are declared as a shared model
The system SHALL define the portfolio's sections (Hero/About, Skills & Stack, Experience/Timeline, Projects, Contact) as a single ordered list/sealed model in `:shared`, so section order and identity are defined once and consumed by both the shell and each section's content.

#### Scenario: Adding a new section later
- **WHEN** a future change needs to add or reorder a portfolio section
- **THEN** it only needs to update the shared section model, not the rendering shell itself

### Requirement: Shell renders all sections in a single scrollable page
The system SHALL render every declared section, in order, inside one scrollable page (single-page-application style), on every target.

#### Scenario: Placeholder content before sections are implemented
- **WHEN** a section has no content implementation yet
- **THEN** the shell renders a placeholder for that section showing its title, so the app builds and runs end-to-end before content changes land

#### Scenario: Full content after a section change lands
- **WHEN** a section's content composable is implemented (by a follow-up change)
- **THEN** the shell renders that composable in place of its placeholder, without shell code changes

### Requirement: Each section owns a single, isolated file
The system SHALL implement each section's `Content()` composable in its own dedicated file under `navigation/sections/`, separate from the shell and the section registry, so that implementing one section's content never requires editing the shell, the registry, or another section's file.

#### Scenario: Two sections implemented in parallel
- **WHEN** two different changes each implement a different section's `Content()`
- **THEN** neither change needs to modify the shell, the registry, or the other section's file, and both can be applied without merge conflicts

### Requirement: Shell is target-agnostic
The system SHALL expose the navigation shell as a single composable entry point that every platform's `App()` call can render unchanged, requiring no per-target navigation code.

#### Scenario: Same entry point across targets
- **WHEN** `webApp`, `androidApp`, `desktopApp`, or `iosApp` invoke the shared `App()` composable
- **THEN** all four render the same shell and section order

### Requirement: Shell hosts the language-switcher control
The system SHALL render the manual language-override control from `PortfolioShell`, visible regardless of scroll position, without introducing a section-anchor navigation system.

#### Scenario: Control visible at any scroll position
- **WHEN** a visitor scrolls to any point on the single-page portfolio
- **THEN** the language-switcher control remains visible and usable
