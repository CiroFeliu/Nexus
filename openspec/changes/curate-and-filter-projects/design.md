## Context

`ProjectsSection.projects` is a static `List<Project>` (16 entries today) rendered by a stateless `Content()` composable directly into one `FlowRow`. `Project` already carries `company: String` and `techStack: List<String>` — both usable for filtering without new data fields beyond one small addition (see below). Note: three projects (`Internal Tools`, `Device Communications`, `Zenith`) have no `link`, which is already a pre-existing gap against the `projects-section` requirement "Project cards link out to repo/demo" — that inconsistency predates this change and is out of scope here.

## Goals / Non-Goals

**Goals:**
- A recruiter scanning quickly sees the 4-5 strongest projects immediately, without scrolling or interacting.
- A visitor who wants to browse everything can narrow 16 cards down by company or by a differentiating tag.
- No filter option that fails to meaningfully narrow the grid (ruling out `Android`/`iOS`).

**Non-Goals:**
- No multi-select filtering (e.g. "Rudo" AND "AI" simultaneously) — single active filter at a time keeps the state model and empty-state handling simple for a 16-item grid.
- No backend/URL-param-driven filter state — resets on reload, same as the rest of the app's client-only state.
- No change to card content/link requirements — reuses `projects-section` as-is.

## Decisions

- **Data model**: add `featured: Boolean = false` to `Project`. Default `false` keeps all 11 non-featured entries unchanged; the 4-5 curated ones get `featured = true`. Simpler than a parallel `featuredProjects` list that could drift out of sync with `projects`.
- **Filter option derivation**: compute filter chips from the data itself rather than hand-maintaining an allowlist — `(projects.map { it.company } + projects.flatMap { it.techStack }.filterNot { it in EXCLUDED_FILTER_TAGS }).distinct()`, where `EXCLUDED_FILTER_TAGS = setOf("Android", "iOS")`. This way, adding a future project with a new differentiating tag automatically gets a filter chip without a second edit; `Android`/`iOS` stay visible as informational `TechTag`s on cards, just never offered as filter chips.
- **Filter semantics**: single active filter (`remember { mutableStateOf<String?>(null) }`), `null` = "All"/"Todos". A project matches when `project.company == selected || selected in project.techStack`.
- **Featured section**: always renders the `featured == true` projects, unaffected by the filter state — it's a fixed curation, not a filtered view. Rendered above the filter row and full grid, with a distinct "Featured"/"Destacados" label. Reuses the existing `ProjectCard` composable (no new card variant) to avoid duplicating card layout — the section is set apart by its label and position, not a different visual style, keeping this change additive rather than a card redesign.
- **Full grid stays complete**: the full grid below still shows all 16 (filtered per selection) including the featured ones — no exclusion logic needed, matching the common "highlights reel + everything" portfolio pattern and avoiding extra branching.
- **Chip component**: Material3 `FilterChip` for the filter row (selected state built in) instead of repurposing the existing plain `Surface`-based `TechTag`, since `FilterChip` already provides selected/unselected styling via `MaterialTheme` colors — no custom selection-state styling to build.

## Risks / Trade-offs

- [Risk] The 5th featured slot (currently Mhia) links to a PDF report, not a product — weaker "click to see it" experience than the other four featured picks. → Mitigation: flagged as an open decision below; can be swapped for HCB Paciente or Zenith without any structural change (just moving the `featured = true` flag).
- [Risk] Derived filter chips mean the exact set of chips shown depends on current data — if all differentiating-tag projects get removed later, chips could shrink unexpectedly. → Mitigation: acceptable given the alternative (hand-maintained list) drifts silently instead; a shrinking chip list is a visible, honest reflection of the data.

## Open Questions

- Final 5th featured project: confirm Mhia vs. HCB Paciente vs. Zenith before/during apply.
