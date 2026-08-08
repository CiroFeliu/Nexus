## ADDED Requirements

### Requirement: CV content is derived from shared portfolio content
The system SHALL define a single CV content model in `:shared`, built from the same name/role/bio/skills/experience/projects/contact data already rendered by the portfolio sections, so the exported CV never duplicates or drifts from the live portfolio content.

#### Scenario: Portfolio content changes
- **WHEN** a section's underlying content (e.g. an Experience entry) is updated
- **THEN** the next CV export reflects the updated content automatically, with no separate resume file to edit

### Requirement: CV can be exported to PDF on every target
The system SHALL allow a visitor to export the CV content to a PDF document on `:webApp`, `:androidApp`, `:iosApp`, and `:desktopApp`, using each platform's native PDF/print mechanism.

#### Scenario: User exports the CV
- **WHEN** a visitor triggers the "Download CV" action on any of the four targets
- **THEN** a PDF document containing the current CV content is produced

### Requirement: Exported CV is delivered via the platform's native mechanism
The system SHALL deliver the generated PDF using each platform's idiomatic delivery method: a browser download/print dialog on web, a share/save action on Android and iOS, and a native save dialog on desktop.

#### Scenario: Delivery matches the platform
- **WHEN** the "Download CV" action completes on a given target
- **THEN** the user is offered the PDF through that target's native download, share, or save flow — not a custom in-app viewer
