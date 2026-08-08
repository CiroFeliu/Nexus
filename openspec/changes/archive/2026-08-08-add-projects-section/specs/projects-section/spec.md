## ADDED Requirements

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
