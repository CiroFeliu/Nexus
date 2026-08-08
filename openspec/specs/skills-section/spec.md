## Requirements

### Requirement: Skills are grouped by category
The Skills & Stack section SHALL present skills grouped under labeled categories (e.g., Languages, Mobile/Android, Architecture & Patterns, Tools & Platforms) rather than as a single flat list.

#### Scenario: Section renders grouped skills
- **WHEN** the portfolio shell renders the Skills & Stack section
- **THEN** each category label is visible with its associated skills grouped beneath it

### Requirement: Skill layout reflows across screen sizes
The skills grid/list SHALL reflow to remain readable across narrow (mobile) and wide (desktop) viewports.

#### Scenario: Narrow viewport
- **WHEN** the section renders on a narrow viewport (e.g., a phone-sized window)
- **THEN** skill chips wrap onto additional lines instead of overflowing or being clipped
