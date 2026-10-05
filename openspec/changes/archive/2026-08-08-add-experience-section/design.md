## Context

Career history is inherently chronological. A timeline layout (vertical line + entries) communicates progression more clearly than a plain list and fits the "structured, technical" design tone.

## Goals / Non-Goals

**Goals:**
- Present roles most-recent-first with clear date ranges, titles, and companies.
- Keep the data model simple (a list of role entries) so updating history later is a one-file edit.

**Non-Goals:**
- Detailed bullet-by-bullet accomplishments per role — a short 1-2 line summary per role is enough for v1; a "read more" expansion can be a later enhancement.
- Company logos — text-only entries for v1.

## Decisions

- **Data-as-code**: role entries defined as a `List<ExperienceEntry>` constant inside `ExperienceSection.kt`, consistent with the isolation contract from the foundation change.
- **Vertical timeline via `Column` + a themed connector line/dot per entry**, rather than pulling in a third-party timeline library — keeps the dependency surface small.

## Risks / Trade-offs

- [Risk] Long descriptions could unbalance the timeline's visual rhythm → Mitigation: cap entry descriptions at ~2 lines by convention when writing the copy.
