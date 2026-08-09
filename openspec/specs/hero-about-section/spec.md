# hero-about-section Specification

## Purpose
TBD - created by archiving change add-hero-about-section. Update Purpose after archive.
## Requirements
### Requirement: Hero/About displays identity and role
The Hero/About section SHALL display Ciro's name, role title, and a short bio, using only `PortfolioTheme` colors and typography.

#### Scenario: Section renders on any target
- **WHEN** the portfolio shell renders the Hero/About section on any of the four targets
- **THEN** the name, role title, and bio text are visible using theme-defined typography and colors

### Requirement: Hero/About displays an avatar placeholder
The Hero/About section SHALL display an avatar (initials-based placeholder or photo) near the identity content.

#### Scenario: No photo asset supplied yet
- **WHEN** no final photo asset has been added to the project
- **THEN** the section renders an initials-based avatar instead of a broken image reference

#### Scenario: Photo asset supplied
- **WHEN** a headshot photo asset has been added to `composeResources`
- **THEN** the section renders that photo as the avatar instead of the initials placeholder, on every target

### Requirement: Hero/About content is available in English and Spanish
The Hero/About section SHALL render its name, role title, and bio in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Hero/About section's role title and bio render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Hero/About section's role title and bio render in English

