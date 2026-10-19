## 1. Dates

- [x] 1.1 Introduce `YearMonth` and replace `ExperienceEntry.dateRange` with `start`/`end`
- [x] 1.2 Add `formatDateRange(start, end, language)` with English/Spanish month abbreviations and "Present"/"actualidad"
- [x] 1.3 Use the formatter in `ExperienceSection` and in the CV content provider
- [x] 1.4 Unit tests for both languages, including open-ended ranges

## 2. Copy

- [x] 2.1 Remove em/en dashes from contact closing line, project unavailable notes and CV headings
- [x] 2.2 Rewrite experience descriptions in first person (EN + ES)
- [x] 2.3 Rewrite third-person project descriptions in first person (EN + ES)
- [x] 2.4 Sentence case for skill category labels and section titles (EN + ES)
- [x] 2.5 Remove the duplicated "shared across platforms" sentence from the hero bio and Nexus description
- [x] 2.6 Ciro reviews the rewritten copy

## 3. Guards and verification

- [x] 3.1 Add a `commonTest` asserting no `—`/`–` in any `Language`-keyed content or CV output
- [x] 3.2 Run `./gradlew test`
- [x] 3.3 Check both languages in the web app and in a generated CV
- [x] 3.4 `openspec validate polish-portfolio-copy --strict`
