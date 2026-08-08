## Context

Ciro's skill set spans deep Android/Kotlin expertise plus broader mobile-systems-architecture concerns (multiplatform, design systems, tooling). A flat list would undersell the structure; grouping communicates seniority better than a wall of tags.

## Goals / Non-Goals

**Goals:**
- Group skills into clearly labeled categories, scannable in a few seconds.
- Keep the data model simple (a list of category → skill-list pairs) so updating skills later is a one-file edit.

**Non-Goals:**
- Skill proficiency bars/ratings — categorized presence is enough; ratings read as subjective/gimmicky for a senior profile.
- Icons per technology — nice-to-have, not required for v1 (theme-consistent text chips are sufficient).

## Decisions

- **Data-as-code**: categories and skills defined as a `List<SkillCategory>` constant inside `SkillsSection.kt`, not an external file/JSON — keeps this section self-contained per the foundation's isolation contract.
- **Chip/flow-row layout** using Compose `FlowRow` (or manual wrapping row) so it reflows naturally across target screen sizes (mobile-narrow to desktop-wide).

## Risks / Trade-offs

- [Risk] Category list could grow stale as Ciro's stack evolves → Mitigation: keep the constant simple and well-commented so future edits are trivial; out of scope to build tooling for this now.
