## ADDED Requirements

### Requirement: Experience content is available in English and Spanish
The Experience/Timeline section SHALL render each entry's role title and description in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** each timeline entry's role title and description render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** each timeline entry's role title and description render in English
