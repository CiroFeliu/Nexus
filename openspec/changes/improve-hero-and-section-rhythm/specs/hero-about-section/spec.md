## MODIFIED Requirements

### Requirement: Hero/About displays an avatar placeholder
The Hero/About section SHALL display an avatar (initials-based placeholder or photo) near the identity content.

#### Scenario: No photo asset supplied yet
- **WHEN** no final photo asset has been added to the project
- **THEN** the section renders an initials-based avatar instead of a broken image reference

#### Scenario: Photo asset supplied
- **WHEN** a headshot photo asset has been added to `composeResources`
- **THEN** the section renders that photo as the avatar instead of the initials placeholder, on every target
