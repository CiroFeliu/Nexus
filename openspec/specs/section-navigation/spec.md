# section-navigation Specification

## Purpose
Let a visitor jump directly to any portfolio section instead of scrolling past everything else: a sticky navigation control, visible regardless of scroll position, with one link per declared section that scrolls to it on activation and highlights whichever section is currently in view. Rendered as a single shared composable so it behaves identically across all four targets.

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

### Requirement: Navigation links to the blog
The navigation bar on non-compact layouts and the compact full-screen menu SHALL each show a "Blog" entry after the section links, visually set apart from them and never highlighted as the active section, that opens the blog: `/blog/` of the current site on the web, `https://cirofeliu.es/blog/` on Android, iOS and desktop.

#### Scenario: Visitor opens the blog from the bar
- **WHEN** a visitor on a non-compact layout activates the Blog entry
- **THEN** the blog list opens through the shared URL-opening capability and the portfolio's scroll position is left unchanged

#### Scenario: Visitor opens the blog from the compact menu
- **WHEN** a visitor on a compact layout opens the menu and activates the Blog entry
- **THEN** the menu closes and the blog list opens

#### Scenario: Native app
- **WHEN** a visitor activates the Blog entry on Android, iOS or desktop
- **THEN** `https://cirofeliu.es/blog/` opens in the platform's browser
