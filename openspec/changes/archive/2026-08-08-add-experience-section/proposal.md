## Why

`add-portfolio-foundation` creates `navigation/sections/ExperienceSection.kt` with a placeholder body. This change fills it in with Ciro's work history as a timeline, giving visitors a chronological view of his career progression.

**Depends on:** `add-portfolio-foundation` must be applied first.

## What Changes

- Implement `ExperienceSection.Content()`: a vertical timeline of roles (company, title, date range, 1-2 line description), most recent first.
- Use only shared theme tokens for colors/typography/shape.

## Capabilities

### New Capabilities
- `experience-section`: The Experience/Timeline section's content — chronological work history.

### Modified Capabilities
(none)

## Impact

- `:shared/src/commonMain/navigation/sections/ExperienceSection.kt` only. No shell, registry, or other section files are touched.
