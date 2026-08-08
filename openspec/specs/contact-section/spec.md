## Requirements

### Requirement: Contact section provides direct links
The Contact section SHALL display direct links to reach Ciro: email, LinkedIn, and GitHub, each opened via the shared `openUrl` capability, and SHALL provide a "Download CV" action that triggers the shared CV export capability.

#### Scenario: User activates a contact link
- **WHEN** a user activates the email, LinkedIn, or GitHub link in the Contact section on any of the four targets
- **THEN** the corresponding `mailto:`/profile URL opens via the platform's default handler

#### Scenario: User downloads the CV
- **WHEN** a user activates the "Download CV" action in the Contact section on any of the four targets
- **THEN** the CV export capability generates a PDF from the current portfolio content and delivers it via that platform's native download/share/save mechanism

### Requirement: No contact form in v1
The Contact section SHALL NOT include a contact form or any client-side-only submission mechanism in v1, since there is no backend to receive submissions yet.

#### Scenario: Visitor looks for a form
- **WHEN** a visitor views the Contact section
- **THEN** they see direct links only, with no non-functional or fake form present
