## MODIFIED Requirements

### Requirement: Filter chips narrow the full projects grid
The Projects section SHALL render a row of filter chips for the list of non-featured projects, one per distinct company and one per distinct differentiating tech tag present in that list, excluding tags too common to narrow it meaningfully.

#### Scenario: Visitor selects a company chip
- **WHEN** a visitor selects a company filter chip
- **THEN** the project list shows only non-featured projects whose `company` matches the selected value

#### Scenario: Visitor selects a tag chip
- **WHEN** a visitor selects a tag filter chip
- **THEN** the project list shows only non-featured projects whose tech stack includes the selected tag

#### Scenario: Visitor resets the filter
- **WHEN** a visitor selects the "All"/"Todos" option (or deselects the active chip)
- **THEN** the project list shows every non-featured project again

#### Scenario: Filter matches nothing
- **WHEN** the selected filter matches no project in the list
- **THEN** the list shows a short message in the active language instead of an empty area
