# projects-section Specification

## Purpose
Give a visitor concrete evidence of Ciro's work: his featured projects rendered as cards, each with a title, short description, tech tags, and a link that opens the project's repo or live demo via the shared `openUrl` capability. Content is bilingual (English/Spanish), sourced from the shared `Language`-keyed content model.
## Requirements
### Requirement: Projects render as a card showcase
The Projects section SHALL render Ciro's featured projects as cards, each showing a title, short description, and tech tags.

#### Scenario: Section renders project cards
- **WHEN** the portfolio shell renders the Projects section
- **THEN** each featured project is visible as a card with title, description, and tech tags

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

