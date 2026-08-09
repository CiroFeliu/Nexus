## ADDED Requirements

### Requirement: Contact section shows the email address as visible text
The Contact section SHALL display the email address as visible, selectable text, in addition to the existing "Email" `mailto:` action.

#### Scenario: Visitor views the Contact section
- **WHEN** a visitor views the Contact section on any target
- **THEN** the email address is visible as plain text near the contact links, independent of whether they activate the "Email" button

### Requirement: Contact section includes a closing statement
The Contact section SHALL display a short closing statement, sourced from the shared `Language`-keyed content, in the active language.

#### Scenario: Active language is Spanish
- **WHEN** the active language is Spanish
- **THEN** the Contact section shows its closing statement in Spanish

#### Scenario: Active language is English
- **WHEN** the active language is English
- **THEN** the Contact section shows its closing statement in English
