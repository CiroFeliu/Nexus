# i18n-support Specification

## Purpose
Let the portfolio greet each visitor in their own language automatically — English or Spanish, resolved from system locale on first load, defaulting to English otherwise — while still letting them switch manually via a control visible regardless of scroll position. A manual override persists across sessions on every target, replacing the original English-only hardcoded copy.

## Requirements

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

### Requirement: Dates are formatted per active language
Dates shown in the portfolio and in the exported CV SHALL be stored as structured values and formatted according to the active language, including the word used for an ongoing period.

#### Scenario: Ongoing role in Spanish
- **WHEN** the active language is Spanish and an experience entry has no end date
- **THEN** its date range renders with Spanish month abbreviations and "actualidad" as the end

#### Scenario: Ongoing role in English
- **WHEN** the active language is English and an experience entry has no end date
- **THEN** its date range renders with English month abbreviations and "Present" as the end

### Requirement: Visible copy follows a shared style
All visible portfolio copy and CV output, in every supported language, SHALL use first person for Ciro's own work, sentence case for multi-word labels and titles, and SHALL NOT use em dashes or en dashes as separators.

#### Scenario: Content is checked
- **WHEN** the shared test suite inspects every `Language`-keyed string and the generated CV text
- **THEN** none of them contains an em dash or en dash character

#### Scenario: Visitor reads an experience entry
- **WHEN** a visitor reads any experience or project description in either language
- **THEN** it is written in first person, consistent with the Hero/About bio
