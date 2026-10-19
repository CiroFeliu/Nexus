## MODIFIED Requirements

### Requirement: Skill layout reflows across screen sizes
The skills layout SHALL arrange category groups in up to four columns on wide layouts, fewer on medium layouts and a single column on compact layouts, with each category's skills rendered as a list that stays readable at every width.

#### Scenario: Narrow viewport
- **WHEN** the section renders on a narrow viewport (e.g., a phone-sized window)
- **THEN** the categories stack in one column and no skill text overflows or is clipped

#### Scenario: Wide viewport
- **WHEN** the section renders at expanded or wider layouts
- **THEN** the four categories render side by side as columns
