## Why

`add-portfolio-foundation` creates `navigation/sections/ProjectsSection.kt` with a placeholder body. This change fills it in with a showcase of Ciro's featured projects (including Nexus itself), giving visitors concrete evidence of his work.

**Depends on:** `add-portfolio-foundation` must be applied first.

## What Changes

- Implement `ProjectsSection.Content()`: a grid/list of project cards (title, short description, tech tags, links to repo/demo).
- Use only shared theme tokens for colors/typography/shape.

## Capabilities

### New Capabilities
- `projects-section`: The Projects section's content — a showcase of featured project cards.

### Modified Capabilities
(none)

## Impact

- `:shared/src/commonMain/navigation/sections/ProjectsSection.kt` only. No shell, registry, or other section files are touched.
