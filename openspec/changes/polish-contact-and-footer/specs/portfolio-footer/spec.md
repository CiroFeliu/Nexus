## ADDED Requirements

### Requirement: Page renders a single footer after all sections
The system SHALL render a single footer, once, after the last `PortfolioSection`'s content, containing a copyright line and a short build-tech note, using `PortfolioTheme` colors and typography only.

#### Scenario: Visitor scrolls to the end of the page
- **WHEN** a visitor scrolls past the Contact section
- **THEN** they see one footer with a copyright line and a build-tech note, and no additional footer appears elsewhere on the page

### Requirement: Footer content is available in English and Spanish
The footer SHALL render its text in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the footer text renders in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the footer text renders in English
