# experience-section Specification

## Purpose
TBD - created by archiving change add-experience-section. Update Purpose after archive.
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

### Requirement: Experience content is available in English and Spanish
The Experience/Timeline section SHALL render each entry's role title and description in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** each timeline entry's role title and description render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** each timeline entry's role title and description render in English

