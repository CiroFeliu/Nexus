## ADDED Requirements

### Requirement: Skills content is available in English and Spanish
The Skills & Stack section SHALL render its category labels in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Skills & Stack section's category labels render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Skills & Stack section's category labels render in English
