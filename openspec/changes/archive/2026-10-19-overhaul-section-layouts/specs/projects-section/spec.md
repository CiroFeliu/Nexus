## MODIFIED Requirements

### Requirement: Projects render as a card showcase
The Projects section SHALL render the featured projects as a bento grid with exactly one tile per featured project, each tile showing a visual (a project image when available, otherwise a designed themed fallback), the company, title, short description and tech tags, and SHALL render the remaining projects once, as an index list below it, so no project appears twice.

#### Scenario: Section renders project cards
- **WHEN** the portfolio shell renders the Projects section
- **THEN** each featured project is visible as exactly one bento tile with a visual, company, title, description, and tech tags, and the grid has no empty cells

#### Scenario: Remaining projects render once
- **WHEN** the portfolio shell renders the Projects section with no filter selected
- **THEN** every non-featured project appears exactly once in the index list, and no featured project appears in it
