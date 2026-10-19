## ADDED Requirements

### Requirement: Navigation is a single floating bar
The section navigation, the name wordmark and the language toggle SHALL render together in one bar on a single line that floats above the page content, transparent while the page is at the top and solid with a separator once the page is scrolled.

#### Scenario: Visitor scrolls past the top
- **WHEN** the scroll position moves away from the top of the page
- **THEN** the navigation bar switches to a solid theme surface with a separator, and switches back when the visitor returns to the top

### Requirement: Compact navigation uses a full-screen menu
On compact layouts the section links SHALL be reachable through a full-screen menu opened from the navigation bar, which can be closed with a close control, the Escape key or the platform back action.

#### Scenario: Visitor navigates on a phone-sized window
- **WHEN** a visitor on a compact layout opens the menu and selects a section
- **THEN** the menu closes and the page scrolls to that section
