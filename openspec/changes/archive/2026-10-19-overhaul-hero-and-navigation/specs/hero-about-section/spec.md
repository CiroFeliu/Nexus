## ADDED Requirements

### Requirement: Hero offers primary actions
The Hero/About section SHALL offer a primary "Download CV" action that triggers the shared CV export, and a secondary "Contact" action that scrolls the page to the Contact section, with labels in the active language.

#### Scenario: Visitor uses the contact action
- **WHEN** a visitor activates the hero's contact action
- **THEN** the page scrolls until the Contact section is visible below the navigation bar

#### Scenario: Visitor downloads the CV from the hero
- **WHEN** a visitor activates the hero's download action
- **THEN** the same CV export as the Contact section's "Download CV" action runs

### Requirement: Hero owns the first viewport
The Hero/About section SHALL fill most of the first viewport on medium and wider layouts and show the name (at most two lines), role, a bio of at most 20 words and both actions without scrolling, in every supported language.

#### Scenario: Desktop first load
- **WHEN** the portfolio loads in a 1280x720 window
- **THEN** the name, role, bio and both hero actions are visible without scrolling and the name occupies at most two lines

### Requirement: Hero uses an asymmetric, width-adaptive layout
The Hero/About section SHALL place the text in a wider left column and the portrait in a narrower right column on medium and wider layouts, and stack them on compact layouts, reserving the portrait's space before the image loads.

#### Scenario: Compact width
- **WHEN** the available width is below the compact breakpoint
- **THEN** the text, actions and portrait stack vertically with no horizontal overflow and no overlap with the next section
