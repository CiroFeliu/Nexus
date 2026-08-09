# project-filtering Specification

## Purpose
TBD - created by archiving change curate-and-filter-projects. Update Purpose after archive.
## Requirements
### Requirement: Filter chips narrow the full projects grid
The Projects section SHALL render a row of filter chips, one per distinct company and one per distinct differentiating tech tag present in the project data, excluding tags too common to narrow the grid meaningfully.

#### Scenario: Visitor selects a company chip
- **WHEN** a visitor selects a company filter chip
- **THEN** the full grid shows only projects whose `company` matches the selected value

#### Scenario: Visitor selects a tag chip
- **WHEN** a visitor selects a tag filter chip
- **THEN** the full grid shows only projects whose tech stack includes the selected tag

#### Scenario: Visitor resets the filter
- **WHEN** a visitor selects the "All"/"Todos" option (or deselects the active chip)
- **THEN** the full grid shows every project again

### Requirement: Overly common tags are excluded from filter chips
The system SHALL NOT offer a filter chip for a tech tag shared by most of the catalogued projects (currently `Android` and `iOS`), since selecting it would not meaningfully narrow the grid; such tags remain visible as informational tags on individual project cards.

#### Scenario: Filter row renders
- **WHEN** the filter chip row renders
- **THEN** it does not include a chip for `Android` or `iOS`, even though those tags still appear on individual project cards

### Requirement: Filter control labels are available in English and Spanish
Any filter-related labels (e.g. the "All"/"Todos" reset option) SHALL render in the active language, sourced from the shared `Language`-keyed content. Company names and tech tag values themselves are proper nouns/technical terms and are not translated.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the reset option and any other filter-control labels render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the reset option and any other filter-control labels render in English

