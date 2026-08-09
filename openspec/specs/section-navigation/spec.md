# section-navigation Specification

## Purpose
TBD - created by archiving change add-section-navigation. Update Purpose after archive.
## Requirements
### Requirement: Sticky navigation links to every portfolio section
The system SHALL render a sticky navigation control, visible regardless of scroll position, containing one link per declared `PortfolioSection`.

#### Scenario: All sections represented
- **WHEN** the portfolio shell renders
- **THEN** the navigation control shows exactly one link per entry in the `PortfolioSection` model, in the same order sections appear on the page

### Requirement: Clicking a link scrolls to its section
The system SHALL scroll the page to bring the selected section into view when its navigation link is activated.

#### Scenario: Visitor clicks a section link
- **WHEN** a visitor activates the link for a section that is not currently in view
- **THEN** the page scrolls until that section is visible at the top of the viewport

### Requirement: Active section is highlighted
The system SHALL visually indicate which section is currently in view as the visitor scrolls, using `PortfolioTheme` colors/typography only.

#### Scenario: Visitor scrolls past a section boundary
- **WHEN** the visitor's scroll position moves into a new section
- **THEN** the navigation link for that section is visually distinguished from the other links, and the previously active link returns to its default appearance

### Requirement: Navigation control is target-agnostic
The system SHALL expose the section-navigation control as a single shared composable that renders identically on `webApp`, `androidApp`, `desktopApp`, and `iosApp`.

#### Scenario: Same control across targets
- **WHEN** any of the four platform targets render `PortfolioShell`
- **THEN** all four show the same navigation links and highlighting behavior

