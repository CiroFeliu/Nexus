# experience-section Specification

## Purpose
Give a visitor a chronological view of Ciro's career progression: a most-recent-first timeline of roles, each with company, title, date range, and a short description. Content is bilingual (English/Spanish), sourced from the shared `Language`-keyed content model.

## Requirements

### Requirement: Experience is shown as a chronological timeline
The Experience/Timeline section SHALL render Ciro's work history as a vertical timeline, ordered most-recent-first, with each entry showing company, role title, and date range.

#### Scenario: Section renders timeline entries
- **WHEN** the portfolio shell renders the Experience/Timeline section
- **THEN** each role entry is visible with company, title, and date range, in most-recent-first order

### Requirement: Each entry includes a short description
Each timeline entry SHALL include a short description of the role of at most two sentences.

#### Scenario: Entry description is concise
- **WHEN** a timeline entry is rendered
- **THEN** its description has at most two sentences and does not exceed roughly 4 lines of themed body text on a 1280px-wide layout

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
