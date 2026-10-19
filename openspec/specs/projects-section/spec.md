# projects-section Specification

## Purpose
Give a visitor concrete evidence of Ciro's work: his featured projects rendered as cards, each with a title, short description, tech tags, and a link that opens the project's repo or live demo via the shared `openUrl` capability. Content is bilingual (English/Spanish), sourced from the shared `Language`-keyed content model.

## Requirements

### Requirement: Projects render as a card showcase
The Projects section SHALL render the featured projects as a bento grid with exactly one tile per featured project, each tile showing a visual (a project image when available, otherwise a designed themed fallback), the company, title, short description and tech tags, and SHALL render the remaining projects once, as an index list below it, so no project appears twice.

#### Scenario: Section renders project cards
- **WHEN** the portfolio shell renders the Projects section
- **THEN** each featured project is visible as exactly one bento tile with a visual, company, title, description, and tech tags, and the grid has no empty cells

#### Scenario: Remaining projects render once
- **WHEN** the portfolio shell renders the Projects section with no filter selected
- **THEN** every non-featured project appears exactly once in the index list, and no featured project appears in it

### Requirement: Project cards link out to repo/demo
Each project card SHALL provide a link that opens the project's repository or live demo in the platform's default browser/handler.

#### Scenario: User opens a project link
- **WHEN** a user activates a project's repo/demo link on any of the four targets
- **THEN** the corresponding URL opens in the platform's default browser or handler

### Requirement: Projects content is available in English and Spanish
The Projects section SHALL render each project card's title and description in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** each project card's title and description render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** each project card's title and description render in English
