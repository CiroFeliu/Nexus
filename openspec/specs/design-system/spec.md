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

