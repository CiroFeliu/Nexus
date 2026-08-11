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
The skills grid/list SHALL reflow to remain readable across narrow (mobile) and wide (desktop) viewports.

#### Scenario: Narrow viewport
- **WHEN** the section renders on a narrow viewport (e.g., a phone-sized window)
- **THEN** skill chips wrap onto additional lines instead of overflowing or being clipped

### Requirement: Skills content is available in English and Spanish
The Skills & Stack section SHALL render its category labels in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Skills & Stack section's category labels render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Skills & Stack section's category labels render in English
