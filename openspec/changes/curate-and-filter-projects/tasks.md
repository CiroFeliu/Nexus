## 1. Data model

- [ ] 1.1 Add `featured: Boolean = false` to `ProjectsSection.Project`
- [ ] 1.2 Mark Nexus, Revieve, DuoxMe, and ShogunAi as `featured = true`
- [ ] 1.3 Confirm the 5th featured pick (Mhia, vs. HCB Paciente or Zenith as alternatives) and mark it `featured = true`

## 2. Filter derivation & state

- [ ] 2.1 Compute the filter chip option list from `projects` (distinct companies + distinct tags, excluding `Android`/`iOS`)
- [ ] 2.2 Add `remember { mutableStateOf<String?>(null) }` filter state to `Content()`
- [ ] 2.3 Implement match logic: company equality OR tag membership

## 3. UI

- [ ] 3.1 Add the "Featured"/"Destacados" subsection above the full grid, rendering only `featured == true` projects via the existing `ProjectCard`
- [ ] 3.2 Add the filter chip row (Material3 `FilterChip`) above the full grid, including an "All"/"Todos" reset chip
- [ ] 3.3 Filter the full grid's `projects.forEach` by the active filter state
- [ ] 3.4 Verify layout on mobile widths (chip row wraps, Featured subsection reflows to single column)

## 4. Tests & verification

- [ ] 4.1 Add/extend `commonTest` coverage: filter chip derivation excludes Android/iOS, filter match logic (company + tag), featured list has exactly the intended 5 projects
- [ ] 4.2 Manually verify on `:webApp`: selecting each filter narrows correctly, Featured subsection stays constant while filtering
- [ ] 4.3 Run `./gradlew test` for all modules

## 5. Spec housekeeping

- [ ] 5.1 Confirm `openspec validate curate-and-filter-projects --strict` passes before archiving
