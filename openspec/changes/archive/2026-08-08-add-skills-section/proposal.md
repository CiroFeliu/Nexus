## Why

`add-portfolio-foundation` creates `navigation/sections/SkillsSection.kt` with a placeholder body. This change fills it in with Ciro's technical skill set, grouped so a recruiter or engineer can scan his mobile-systems-architecture depth quickly.

**Depends on:** `add-portfolio-foundation` must be applied first.

## What Changes

- Implement `SkillsSection.Content()`: skills grouped into categories (e.g., Languages, Mobile/Android, Architecture & Patterns, Tools & Platforms), rendered as a chip/tag grid or grouped list.
- Use only shared theme tokens for colors/typography/shape.

## Capabilities

### New Capabilities
- `skills-section`: The Skills & Stack section's content — categorized technical skills.

### Modified Capabilities
(none)

## Impact

- `:shared/src/commonMain/navigation/sections/SkillsSection.kt` only. No shell, registry, or other section files are touched.
