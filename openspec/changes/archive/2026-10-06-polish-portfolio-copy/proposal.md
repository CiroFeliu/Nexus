## Why

The visible copy has several issues that read as unfinished or machine-written, independent of any visual redesign:

- Em-dashes used as separators in the contact closing line, every experience date range, the "not available" project notes and the generated CV headings.
- The Spanish locale shows English dates (`Feb 2025 — Present`, `Aug`, `Dec`), because `ExperienceEntry.dateRange` is a single untranslated string.
- Voice switches between first person (hero: "Diseño y construyo", Nexus project: "my time at Rudo") and third person (experience: "Se incorporó", "Responsable de…", projects: "Colaboró", "durante su etapa").
- Spanish category labels use Title Case ("Arquitectura y Patrones", "Herramientas y Plataformas").
- "Shared across web, Android, desktop and iOS" appears three times (hero bio, Nexus project, footer).

Both redesign variants reuse this content, so it is fixed once here.

## What Changes

- Model experience dates as structured year/month values (with an optional "present" end) and format them per active language (`feb 2025 - actualidad` / `Feb 2025 - Present`), in the UI and in the CV export.
- Replace every em/en dash used as a separator in visible copy and CV output with a period, comma, colon, parentheses or a spaced hyphen.
- Rewrite experience and project descriptions in first person in both languages, keeping their meaning and length.
- Sentence case for multi-word category labels and section titles in Spanish and English.
- Remove the repeated "shared across platforms" sentence from the hero bio and Nexus description, keeping it only in the footer build note. (The hero bio itself is shortened by the variant hero changes.)

## Capabilities

### New Capabilities
- (none)

### Modified Capabilities
- `i18n-support`: adds requirements for language-aware date formatting and a shared copy style for all visible strings.

## Impact

- `navigation/sections/ExperienceSection.kt` (date model + formatting + copy), `ProjectsSection.kt`, `SkillsSection.kt`, `ContactSection.kt`, `HeroAboutSection.kt` (dedupe sentence only)
- `cv/CvContentProvider.kt`, `cv/CvPrintHtml.kt` (dates, separators)
- Tests: date formatting per language, no dash characters in any `Language`-keyed content
- No layout or theme changes.
