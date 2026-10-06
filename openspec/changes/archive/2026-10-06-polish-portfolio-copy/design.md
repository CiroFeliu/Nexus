## Context

Content is stored as `Map<Language, …>` per section (`ExperienceSection.entries`, `ProjectsSection.projects`, `skillCategories`, `ContactSection` labels) and reused by the CV export (`CvContentProvider`, `CvPrintHtml`). Dates are a free-form `dateRange: String` shared by both languages.

## Goals / Non-Goals

**Goals:**
- Every visible string reads naturally in both languages, in one voice.
- Dates follow the active language in the UI and in the CV.
- A test guards against dashes creeping back into content.

**Non-Goals:**
- Hero bio length/structure (owned by each variant's hero change).
- New content (projects, roles, skills).
- Translating proper nouns (company, product and technology names).

## Decisions

- **Structured dates.** Replace `dateRange: String` with `start: YearMonth` and `end: YearMonth?` (`null` = present), using a small local `YearMonth(year, month)` data class rather than adding `kotlinx-datetime` just for month names. A `formatDateRange(start, end, language)` function owns month abbreviations and the "present" word per language. Spanish uses lowercase month abbreviations (`feb 2025 - actualidad`); English uses `Feb 2025 - Present`. Separator is a spaced hyphen.
- **Voice: first person.** It is Ciro's own site; the hero already speaks in first person. Descriptions are rewritten as first person without changing facts ("I joined as an Android developer and became the company's go-to mobile expert" / "Entré como desarrollador Android y acabé siendo el referente móvil de la empresa").
- **Separators.** Contact closing line becomes two short sentences or a comma clause. "Confidential, under NDA" / "Confidencial, bajo NDA". CV headings use `role · company`-free formatting: `Role, Company` (one separator style, no middle dots).
- **Sentence case.** "Architecture and patterns", "Tools and platforms", "Arquitectura y patrones", "Herramientas y plataformas". Navigation labels are single words and unaffected.
- **Guard test.** A `commonTest` walks all `Language`-keyed content exposed by sections and CV provider and asserts no `—` or `–` characters.

## Risks / Trade-offs

- [Risk] The CV export currently shares `dateRange` strings; changing the model touches the CV path. → Covered by the existing `CvContentProviderTest`, extended for both languages.
- [Trade-off] Hand-rolled month names instead of a date library: two languages, twelve months, no time zones; a dependency is not justified.

## Open Questions

- Ciro to confirm the first-person rewrites before implementation is marked done (copy is personal).
