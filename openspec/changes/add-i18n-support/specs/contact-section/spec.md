## ADDED Requirements

### Requirement: Contact section labels are available in English and Spanish
The Contact section SHALL render any user-facing labels (e.g. link labels) in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text. The underlying `mailto:`/profile URLs are unaffected by language.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Contact section's labels render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Contact section's labels render in English
