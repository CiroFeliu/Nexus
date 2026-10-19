# hero-about-section Specification

## Purpose
Give a visitor a real first impression: Ciro's name, role title, short bio, and avatar, rendered with `PortfolioTheme` colors and typography instead of the wizard-scaffold placeholder it replaces. Content is bilingual (English/Spanish), and the avatar degrades gracefully to an initials placeholder until a photo asset is supplied.

## Requirements

### Requirement: Hero/About displays identity and role
The Hero/About section SHALL display Ciro's name, role title, and a short bio, using only `PortfolioTheme` colors and typography.

#### Scenario: Section renders on any target
- **WHEN** the portfolio shell renders the Hero/About section on any of the four targets
- **THEN** the name, role title, and bio text are visible using theme-defined typography and colors

### Requirement: Hero/About displays an avatar placeholder
The Hero/About section SHALL display an avatar (initials-based placeholder or photo) near the identity content.

#### Scenario: No photo asset supplied yet
- **WHEN** no final photo asset has been added to the project
- **THEN** the section renders an initials-based avatar instead of a broken image reference

#### Scenario: Photo asset supplied
- **WHEN** a headshot photo asset has been added to `composeResources`
- **THEN** the section renders that photo as the avatar instead of the initials placeholder, on every target

### Requirement: Hero/About content is available in English and Spanish
The Hero/About section SHALL render its name, role title, and bio in the active language (English or Spanish), sourced from the shared `Language`-keyed content, with no hardcoded single-language text.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Hero/About section's role title and bio render in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Hero/About section's role title and bio render in English

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
