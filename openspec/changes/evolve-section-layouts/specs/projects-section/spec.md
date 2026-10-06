## MODIFIED Requirements

### Requirement: Projects render as a card showcase
The Projects section SHALL render the featured projects as cards, each showing the company, title, short description and tech tags, and SHALL render the remaining projects once, as a compact list of rows below them, so no project appears twice.

#### Scenario: Section renders project cards
- **WHEN** the portfolio shell renders the Projects section
- **THEN** each featured project is visible as a card with company, title, description, and tech tags

#### Scenario: Remaining projects render once
- **WHEN** the portfolio shell renders the Projects section with no filter selected
- **THEN** every non-featured project appears exactly once as a list row, and no featured project appears in that list
