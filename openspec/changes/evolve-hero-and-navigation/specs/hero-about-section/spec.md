## ADDED Requirements

### Requirement: Hero offers primary actions
The Hero/About section SHALL offer a primary "Download CV" action that triggers the shared CV export, and a secondary "Contact" action that scrolls the page to the Contact section, with labels in the active language.

#### Scenario: Visitor uses the contact action
- **WHEN** a visitor activates the hero's contact action
- **THEN** the page scrolls until the Contact section is at the top of the viewport

#### Scenario: Visitor downloads the CV from the hero
- **WHEN** a visitor activates the hero's download action
- **THEN** the same CV export as the Contact section's "Download CV" action runs

### Requirement: Hero fits the first viewport
The Hero/About section SHALL show the name, role, a bio of at most 20 words and both actions within the first viewport on a 1280x720 window, in every supported language.

#### Scenario: Desktop first load
- **WHEN** the portfolio loads in a 1280x720 window
- **THEN** the name, role, bio and both hero actions are visible without scrolling

### Requirement: Hero layout adapts to width
The Hero/About section SHALL place the text and the portrait side by side on medium and wider layouts, and stack them left-aligned on compact layouts, reserving the portrait's space before the image loads.

#### Scenario: Compact width
- **WHEN** the available width is below the compact breakpoint
- **THEN** the portrait appears above the left-aligned text with no horizontal overflow
