# skills-section Specification

## Purpose
Let a recruiter or engineer quickly scan Ciro's technical depth: skills grouped under labeled categories (Languages, Mobile/Android, Architecture & Patterns, Tools & Platforms) rather than a single flat list, reflowing to stay readable from phone-sized to desktop viewports. Category labels are bilingual (English/Spanish), sourced from the shared `Language`-keyed content.

## Requirements

### Requirement: Skills are grouped by category
The Skills & Stack section SHALL present skills grouped under labeled categories (e.g., Languages, Mobile/Android, Architecture & Patterns, Tools & Platforms) rather than as a single flat list.

#### Scenario: Section renders grouped skills
- **WHEN** the portfolio shell renders the Skills & Stack section
- **THEN** each category label is visible with its associated skills grouped beneath it

### Requirement: Skill layout reflows across screen sizes
The skills layout SHALL arrange category groups in up to four columns on wide layouts, fewer on medium layouts and a single column on compact layouts, with each category's skills rendered as a list that stays readable at every width.

#### Scenario: Narrow viewport
- **WHEN** the section renders on a narrow viewport (e.g., a phone-sized window)
- **THEN** the categories stack in one column and no skill text overflows or is clipped

#### Scenario: Wide viewport
- **WHEN** the section renders at expanded or wider layouts
- **THEN** the four categories render side by side as columns

### Requirement: Skills content is available in English and Spanish
The Skills & Stack section SHALL render its category labels in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Skills & Stack section's category labels render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Skills & Stack section's category labels render in English
