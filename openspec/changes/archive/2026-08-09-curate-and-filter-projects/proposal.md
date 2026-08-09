## Why

The Projects section is a single uniform grid of 16 cards with no way to narrow it down, and no signal to a time-constrained recruiter about which 4-5 projects matter most. Some tags (`Android`, `iOS`) are shared by 8-9 of the 16 projects and wouldn't meaningfully narrow anything if used as filters; other tags (`AI`, `Cybersecurity`, `Kotlin Multiplatform`, `Hardware`) are much more differentiating. Separately, the `company` field (already shown on every card) is a natural, high-signal grouping that isn't yet actionable.

## What Changes

- Add a "Featured" subsection above the full projects grid, highlighting the most compelling projects for someone screening the portfolio: **Nexus, Revieve, DuoxMe, ShogunAi, HCB Paciente, and Zenith** (6 total — Mhia was dropped from consideration since its only link is a PDF report rather than a product page; both alternatives considered, HCB Paciente and Zenith, were kept instead).
- Add a filter control above the full grid using `company` (Fermax / S2 Grupo / Rudo / Personal) and the differentiating `techStack` tags (`AI`, `SaaS`, `Cybersecurity`, `Secure Development`, `Kotlin Multiplatform`, `Compose Multiplatform`, `Desktop`, `Hardware`, `FPV`) — **excluding** `Android`/`iOS` as filter options since they cover 8-9 of 16 projects and don't meaningfully narrow the grid (they remain visible as informational tags on each card, just not clickable filters).
- Selecting a filter narrows the full grid to matching projects; an "All"/"Todos" option resets it. The Featured subsection is unaffected by the filter — it's a fixed curated set, not filtered.

## Capabilities

### New Capabilities
- `featured-projects`: a curated subsection of 4-5 projects rendered above the full grid, fixed regardless of filter state.
- `project-filtering`: filter chips (by company and by a defined subset of differentiating tags) that narrow the full projects grid.

### Modified Capabilities
- (none — reuses the existing `Project`/`ProjectContent` data model and the existing `projects-section` card-rendering requirements as-is for both the featured subsection and the filtered grid)

## Impact

- `shared/src/commonMain/kotlin/app/luxion/nexus/navigation/sections/ProjectsSection.kt`: add a `featured: Boolean` (or equivalent) marker to the relevant `Project` entries, add filter state, add the Featured subsection UI, add the filter chip row, and filter the grid accordingly.
- No new dependencies; reuses `FilterChip`-style components already available via Material3 (the existing `TechTag` `Surface` can be adapted or paired with a clickable `FilterChip`).
