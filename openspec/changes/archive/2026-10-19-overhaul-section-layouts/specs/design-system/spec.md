## MODIFIED Requirements

### Requirement: Consecutive sections use alternating background tokens
The system SHALL render all portfolio sections on the same page background token and SHALL separate consecutive sections with spacing and a theme-colored separator line, instead of alternating background bands, so section boundaries stay clear without the page reading as striped.

#### Scenario: Two adjacent sections render
- **WHEN** the portfolio shell renders any two consecutive `PortfolioSection` entries
- **THEN** both use the page `background` token, a theme-colored separator sits between them, and both remain correctly readable in light and dark mode
