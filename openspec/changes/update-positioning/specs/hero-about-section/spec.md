## MODIFIED Requirements

### Requirement: Hero/About displays identity and role
The Hero/About section SHALL display Ciro's name, a short role title and an intro, using only `PortfolioTheme` colors and typography. The role title and the intro together SHALL state Ciro's public headline: the role title is its title part ("Product engineer" / "Ingeniero de producto") and the intro is its statement part ("I build with AI agents in parallel, from spec to real device." / "Desarrollo con agentes de IA en paralelo, de la spec al dispositivo real."). The longer bio is kept as separate shared content for the CV.

#### Scenario: Section renders on any target
- **WHEN** the portfolio shell renders the Hero/About section on any of the four targets
- **THEN** the name, role title, and intro text are visible using theme-defined typography and colors

#### Scenario: Hero states the headline
- **WHEN** the Hero/About section renders in English
- **THEN** the role title reads "Product engineer" and the intro reads "I build with AI agents in parallel, from spec to real device."

#### Scenario: CV uses the full bio
- **WHEN** the CV is exported in either language
- **THEN** its role is the hero's role title and its bio is the three-sentence bio in that language
