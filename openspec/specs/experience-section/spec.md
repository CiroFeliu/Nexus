## Requirements

### Requirement: Experience is shown as a chronological timeline
The Experience/Timeline section SHALL render Ciro's work history as a vertical timeline, ordered most-recent-first, with each entry showing company, role title, and date range.

#### Scenario: Section renders timeline entries
- **WHEN** the portfolio shell renders the Experience/Timeline section
- **THEN** each role entry is visible with company, title, and date range, in most-recent-first order

### Requirement: Each entry includes a short description
Each timeline entry SHALL include a short (1-2 line) description of the role.

#### Scenario: Entry description is concise
- **WHEN** a timeline entry is rendered
- **THEN** its description does not exceed roughly 2 lines of themed body text
