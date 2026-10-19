## ADDED Requirements

### Requirement: Dates are formatted per active language
Dates shown in the portfolio and in the exported CV SHALL be stored as structured values and formatted according to the active language, including the word used for an ongoing period.

#### Scenario: Ongoing role in Spanish
- **WHEN** the active language is Spanish and an experience entry has no end date
- **THEN** its date range renders with Spanish month abbreviations and "actualidad" as the end

#### Scenario: Ongoing role in English
- **WHEN** the active language is English and an experience entry has no end date
- **THEN** its date range renders with English month abbreviations and "Present" as the end

### Requirement: Visible copy follows a shared style
All visible portfolio copy and CV output, in every supported language, SHALL use first person for Ciro's own work, sentence case for multi-word labels and titles, and SHALL NOT use em dashes or en dashes as separators.

#### Scenario: Content is checked
- **WHEN** the shared test suite inspects every `Language`-keyed string and the generated CV text
- **THEN** none of them contains an em dash or en dash character

#### Scenario: Visitor reads an experience entry
- **WHEN** a visitor reads any experience or project description in either language
- **THEN** it is written in first person, consistent with the Hero/About bio
