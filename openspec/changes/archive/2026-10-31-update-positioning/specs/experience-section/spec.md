## MODIFIED Requirements

### Requirement: Experience content is available in English and Spanish
The Experience/Timeline section SHALL render each entry's role title and description in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text. A role title MAY be the same English text in both languages when it is an English job title on purpose; its description SHALL still be translated.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** each timeline entry's role title and description render in Spanish, except role titles deliberately kept in English

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** each timeline entry's role title and description render in English

#### Scenario: Freelance title kept in English
- **WHEN** the Freelance entry renders in Spanish
- **THEN** its role title reads "Contract engineer for AI-first product teams" and its description is in Spanish

### Requirement: Each entry includes a short description
Each timeline entry SHALL include a short description of the role of at most two sentences.

#### Scenario: Entry description is concise
- **WHEN** a timeline entry is rendered
- **THEN** its description has at most two sentences and does not exceed roughly 4 lines of themed body text on a 1280px-wide layout
