# featured-projects Specification

## Purpose
TBD - created by archiving change curate-and-filter-projects. Update Purpose after archive.
## Requirements
### Requirement: A curated Featured subsection highlights top projects
The Projects section SHALL render a "Featured" subsection above the full projects grid, containing exactly the projects marked as featured in the shared project data.

#### Scenario: Section renders
- **WHEN** the portfolio shell renders the Projects section
- **THEN** the Featured subsection appears above the full grid and shows only the projects marked `featured`

### Requirement: Featured subsection is unaffected by filtering
The Featured subsection SHALL always display the same curated projects regardless of any active filter selection in the full grid below it.

#### Scenario: A filter is active
- **WHEN** a visitor has an active filter selected in the full grid's filter control
- **THEN** the Featured subsection's contents do not change

### Requirement: Featured subsection content is available in English and Spanish
The Featured subsection SHALL render each featured project's title and description in the active language, reusing the same `Language`-keyed content as the full grid.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Featured subsection's project titles and descriptions render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Featured subsection's project titles and descriptions render in English

