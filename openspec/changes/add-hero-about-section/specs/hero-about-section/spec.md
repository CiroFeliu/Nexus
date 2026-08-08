## ADDED Requirements

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
