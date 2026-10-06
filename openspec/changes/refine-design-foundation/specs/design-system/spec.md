## ADDED Requirements

### Requirement: Bundled typeface on every target
The shared theme SHALL render all text with typefaces bundled in `:shared` compose resources (Geist for UI and display text, Geist Mono for technical labels such as dates and tech tags), so no target falls back to a platform or engine default font.

#### Scenario: Web target first frame
- **WHEN** the web app shows its first composed frame
- **THEN** all visible text is already rendered in the bundled typefaces, with no visible swap from a fallback font

#### Scenario: Any typography style is used
- **WHEN** a composable reads any `MaterialTheme.typography` style
- **THEN** that style uses the bundled Geist family rather than the default font

### Requirement: Fully specified color schemes
The light and dark color schemes SHALL explicitly define every Material 3 color role from one neutral family and one accent family, so no role resolves to a Material baseline default value.

#### Scenario: Component uses a container role
- **WHEN** a Material component (card, chip, menu) reads a `surfaceContainer*` or `outlineVariant` role
- **THEN** it receives a value from the portfolio's neutral family, not the Material baseline palette

#### Scenario: Contrast in both schemes
- **WHEN** body text, labels or button content render on any theme surface in light or dark mode
- **THEN** the foreground/background pair meets WCAG AA contrast

### Requirement: Content width is capped
Section content SHALL be laid out inside a shared centered container with a maximum content width and breakpoint-dependent horizontal gutters, so content never stretches edge-to-edge on wide screens.

#### Scenario: Wide desktop window
- **WHEN** the portfolio renders in a window wider than the maximum content width
- **THEN** section content is centered and does not exceed the maximum content width

### Requirement: Interactive elements expose consistent states
Every clickable element SHALL show a pointer cursor on hover (where the platform has a pointer), visible hover and pressed feedback, and a visible focus indicator when focused via keyboard.

#### Scenario: Visitor hovers a link on web
- **WHEN** a visitor moves the mouse over any clickable element on the web target
- **THEN** the cursor changes to a pointer and the element shows hover feedback

#### Scenario: Keyboard navigation
- **WHEN** a visitor moves focus with the keyboard
- **THEN** the focused element shows a visible focus indicator

### Requirement: Reduced-motion preference is honoured
The theme SHALL expose the platform's reduced-motion preference to all composables, and non-essential animations SHALL become instant when it is enabled.

#### Scenario: Visitor prefers reduced motion
- **WHEN** the platform reports a reduced-motion preference
- **THEN** entry, hover and scroll-linked animations complete instantly or are skipped, while content remains fully visible and usable
