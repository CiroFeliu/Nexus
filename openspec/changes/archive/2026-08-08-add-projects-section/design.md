## Context

Projects are the strongest evidence of Ciro's mobile-systems-architecture work. This section needs to open external links (GitHub repos, live demos), using the shared `openUrl(url: String)` capability added by `add-portfolio-foundation`.

## Goals / Non-Goals

**Goals:**
- Present a scannable grid/list of project cards: title, short description, tech tags, and repo/demo links.
- Open external links via the existing shared `openUrl` capability — no new platform code in this change.

**Non-Goals:**
- Embedded live previews/iframes of demos — a link out is enough for v1.
- A CMS or remote project data source — projects are defined as code for now, consistent with other sections.
- Defining `openUrl` itself — that lives in `add-portfolio-foundation` precisely so this section and Contact don't both add it.

## Decisions

- **Data-as-code**: project entries defined as a `List<Project>` constant inside `ProjectsSection.kt`.
- **Card grid via a wrapping/flow layout** so it adapts from a single column (narrow) to a multi-column grid (wide), matching the Skills section's responsive approach.

## Risks / Trade-offs

- [Risk] If `add-portfolio-foundation`'s `openUrl` isn't applied yet, this section can't be finished → Mitigation: this change explicitly depends on the foundation change being applied first (see proposal).
