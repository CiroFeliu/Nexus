## MODIFIED Requirements

### Requirement: Hero/About displays identity and role
The Hero/About section SHALL display Ciro's name, a short role title and an intro, using only `PortfolioTheme` colors and typography. The role title and the intro together SHALL state Ciro's public headline, and the longer bio SHALL stay separate shared content used by the CV.

#### Scenario: Section renders on any target
- **WHEN** the portfolio shell renders the Hero/About section on any of the four targets
- **THEN** the name, role title, and intro text are visible using theme-defined typography and colors

#### Scenario: Hero states the headline in English
- **WHEN** the Hero/About section renders in English
- **THEN** the role title reads "Product engineer" and the intro reads "I build with AI agents in parallel, from spec to real device."

#### Scenario: Hero states the headline in Spanish
- **WHEN** the Hero/About section renders in Spanish
- **THEN** the role title reads "Ingeniero de producto" and the intro reads "Desarrollo con agentes de IA en paralelo, de la spec al dispositivo real."

#### Scenario: CV uses the full bio
- **WHEN** the CV is exported in either language
- **THEN** its role is the hero's role title and its bio is the three-sentence bio in that language
