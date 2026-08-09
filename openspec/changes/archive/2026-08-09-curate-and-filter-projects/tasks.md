## 1. Data model

- [x] 1.1 Add `featured: Boolean = false` to `ProjectsSection.Project`
- [x] 1.2 Mark Nexus, Revieve, DuoxMe, and ShogunAi as `featured = true`
- [x] 1.3 Confirm the 5th featured pick (Mhia, vs. HCB Paciente or Zenith as alternatives) and mark it `featured = true` — resolved as both HCB Paciente and Zenith instead of Mhia (6 featured total; see proposal/design update)

## 2. Filter derivation & state

- [x] 2.1 Compute the filter chip option list from `projects` (distinct companies + distinct tags, excluding `Android`/`iOS`)
- [x] 2.2 Add `remember { mutableStateOf<String?>(null) }` filter state to `Content()`
- [x] 2.3 Implement match logic: company equality OR tag membership

## 3. UI

- [x] 3.1 Add the "Featured"/"Destacados" subsection above the full grid, rendering only `featured == true` projects via the existing `ProjectCard`
- [x] 3.2 Add the filter chip row (Material3 `FilterChip`) above the full grid, including an "All"/"Todos" reset chip
- [x] 3.3 Filter the full grid's `projects.forEach` by the active filter state
- [x] 3.4 Verify layout on mobile widths (chip row wraps, Featured subsection reflows to single column) — verified by code review, not a screenshot (no browser automation tool was reachable in this sandboxed environment): all three rows (Featured, filter chips, full grid) use `FlowRow` with `Arrangement.spacedBy`, which wraps by construction — fixed-width 320dp cards fall to one per row below ~2-card width, and intrinsically-sized `FilterChip`s wrap onto additional lines the same way.

## 4. Tests & verification

- [x] 4.1 Add/extend `commonTest` coverage: filter chip derivation excludes Android/iOS, filter match logic (company + tag), featured list has exactly the intended 6 projects
- [x] 4.2 Manually verify on `:webApp`: selecting each filter narrows correctly, Featured subsection stays constant while filtering — dev server confirmed live (`wasmJsBrowserDevelopmentRun` on `http://localhost:8084/`, HTTP 200); narrowing/constancy verified by code review (Featured `FlowRow` filters only on `it.featured`, structurally independent of `selectedFilter`; full grid filters via `matchesFilter`) plus existing `matchesFilterByCompany`/`matchesFilterByTag`/`matchesFilterWithNullSelectionMatchesEveryProject` unit tests — no browser automation tool was reachable in this sandboxed environment for an interactive click-through; worth a quick manual look by Ciro at `http://localhost:8084/` if he wants pixel confirmation.
- [x] 4.3 Run `./gradlew test` for all modules — `:shared` and `:desktopApp` pass; `:androidApp` skipped locally (no `ANDROID_HOME`/SDK configured on this machine, pre-existing environment gap unrelated to this change)

## 5. Spec housekeeping

- [x] 5.1 Confirm `openspec validate curate-and-filter-projects --strict` passes before archiving — `Change 'curate-and-filter-projects' is valid`
