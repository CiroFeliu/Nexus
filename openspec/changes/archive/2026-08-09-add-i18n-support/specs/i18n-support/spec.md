## ADDED Requirements

### Requirement: Supported languages
The system SHALL support English and Spanish as portfolio content languages, modeled as a shared `Language` type in `:shared`.

#### Scenario: Only supported languages are selectable
- **WHEN** a visitor opens the manual language control
- **THEN** only English and Spanish are offered as choices

### Requirement: Language defaults to system locale
The system SHALL resolve the active language on startup from the platform's system locale, defaulting to English when the system locale is neither English nor Spanish.

#### Scenario: System locale is Spanish
- **WHEN** the visitor's system/browser locale is Spanish and no manual override is persisted
- **THEN** the portfolio renders in Spanish on first load

#### Scenario: System locale is unsupported
- **WHEN** the visitor's system/browser locale is neither English nor Spanish and no manual override is persisted
- **THEN** the portfolio renders in English on first load

### Requirement: Visitor can manually override the language
The system SHALL provide a manual language-override control, visible regardless of scroll position, that lets a visitor switch the active language independent of system locale.

#### Scenario: Visitor switches language manually
- **WHEN** a visitor selects a different language from the manual control
- **THEN** all rendered portfolio content updates to the selected language immediately, without a page reload

### Requirement: Manual language override persists across sessions
The system SHALL persist a manually-selected language across app restarts/sessions, on `:webApp`, `:androidApp`, `:iosApp`, and `:desktopApp`.

#### Scenario: Returning visitor with a manual override
- **WHEN** a visitor who previously selected a language manually reopens the portfolio in a new session
- **THEN** the portfolio renders in the previously-selected language, even if the system locale differs

#### Scenario: No manual override yet made
- **WHEN** a visitor has never manually selected a language
- **THEN** the system locale (falling back to English) determines the active language on each visit
