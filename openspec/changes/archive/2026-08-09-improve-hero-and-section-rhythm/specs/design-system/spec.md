## ADDED Requirements

### Requirement: Consecutive sections use alternating background tokens
The system SHALL render consecutive portfolio sections with alternating `MaterialTheme.colorScheme` background/surface tokens, so adjacent sections are visually distinguishable without introducing new colors outside the shared theme.

#### Scenario: Two adjacent sections render
- **WHEN** the portfolio shell renders any two consecutive `PortfolioSection` entries
- **THEN** they use different `colorScheme` tokens (e.g. `background` then `surface`) from the shared theme, and both remain correctly readable in light and dark mode
