# design-system Specification

## Purpose
Defines the shared Material3 theme (colors, typography, shapes) that every portfolio composable and target consumes, so visual tone stays consistent and adapts to system dark mode without per-screen overrides.

## Requirements

### Requirement: Shared Material3 theme
The system SHALL provide a single Material3 `MaterialTheme` wrapper in `:shared` (`commonMain`) that supplies color scheme, typography, and shape tokens to every composable in the portfolio, so no screen defines its own colors or text styles.

#### Scenario: Composable requests theme colors
- **WHEN** any portfolio composable reads `MaterialTheme.colorScheme` or `MaterialTheme.typography`
- **THEN** it receives values from the shared theme definition, not hardcoded literals

### Requirement: Automatic Dark Mode support
The system SHALL switch between a light and a dark color scheme automatically based on the platform's system-level dark mode preference, on every target (Web, Android, iOS, Desktop).

#### Scenario: System is in dark mode
- **WHEN** the host OS/browser reports a dark color scheme preference
- **THEN** the portfolio renders using the dark color scheme without requiring user interaction

#### Scenario: System is in light mode
- **WHEN** the host OS/browser reports a light (or no) color scheme preference
- **THEN** the portfolio renders using the light color scheme

### Requirement: Consistent visual tone across targets
The system SHALL render the same color scheme, typography scale, and shape/spacing tokens on `:webApp`, `:androidApp`, `:iosApp`, and `:desktopApp`, since all four consume the same `:shared` theme package.

#### Scenario: Same theme values on every target
- **WHEN** the app is built and run on any of the four targets
- **THEN** the resolved `MaterialTheme.colorScheme`, `MaterialTheme.typography`, and shape tokens are identical across targets

### Requirement: Consecutive sections use alternating background tokens
The system SHALL render consecutive portfolio sections with alternating `MaterialTheme.colorScheme` background/surface tokens, so adjacent sections are visually distinguishable without introducing new colors outside the shared theme.

#### Scenario: Two adjacent sections render
- **WHEN** the portfolio shell renders any two consecutive `PortfolioSection` entries
- **THEN** they use different `colorScheme` tokens (e.g. `background` then `surface`) from the shared theme, and both remain correctly readable in light and dark mode

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
