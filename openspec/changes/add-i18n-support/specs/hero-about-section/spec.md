## ADDED Requirements

### Requirement: Hero/About content is available in English and Spanish
The Hero/About section SHALL render its name, role title, and bio in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Hero/About section's role title and bio render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Hero/About section's role title and bio render in English
