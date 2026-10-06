## ADDED Requirements

### Requirement: Navigation is a single bar
The section navigation, the name wordmark and the language toggle SHALL render together in one bar that never wraps onto a second line.

#### Scenario: Desktop width
- **WHEN** the portfolio renders at medium or wider layouts
- **THEN** the wordmark, every section link and the language toggle appear on one line in a single bar

### Requirement: Compact navigation uses a menu
On compact layouts the section links SHALL be reachable through a menu opened from the navigation bar, keeping the bar on one line.

#### Scenario: Visitor navigates on a phone-sized window
- **WHEN** a visitor on a compact layout opens the navigation menu and selects a section
- **THEN** the menu closes and the page scrolls to that section
