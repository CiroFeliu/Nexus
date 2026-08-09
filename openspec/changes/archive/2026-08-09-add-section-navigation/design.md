## Context

`PortfolioShell` currently renders `LanguageSwitcher` followed by a single `verticalScroll` `Column` that lays out every `PortfolioSection` in order. There is no per-section anchor, id, or scroll-position awareness anywhere in the shared code today. `PortfolioSection` is the existing ordered model of sections consumed by the shell (per `navigation-shell`).

## Goals / Non-Goals

**Goals:**
- Let a visitor jump to any section from anywhere on the page.
- Show which section is currently in view.
- Keep the nav control target-agnostic like the rest of the shell — same composable renders on Web, Android, iOS, Desktop.

**Non-Goals:**
- No hash-based URL routing (`/#projects`) in this change — that's a separate concern (SPA routing) not currently scoped for v1.
- No hamburger/collapsed mobile menu redesign — the nav row wraps or shrinks using existing `PortfolioSpacing`/`FlowRow` patterns already used elsewhere in the codebase, not a new mobile nav pattern.

## Decisions

- **Section identity for scrolling**: extend `PortfolioSection` with a stable label (already likely present as section title per-language) and a way for the shell to know each section's on-screen position. Simplest cross-platform approach: track each section's `Modifier.onGloballyPositioned` offset within the shared scroll container and drive `ScrollState.animateScrollTo(offset)` on click — this works identically across all four Compose targets, no target-specific code needed.
- **Active-section highlighting**: derive the active section from comparing the current `ScrollState.value` against each recorded section offset (the section whose offset is closest to, but not past, current scroll position). Recomputed as scroll state changes — no separate scroll listener needed since `ScrollState` is already observable.
- **Placement**: the nav sits in the same sticky row as `LanguageSwitcher` in `PortfolioShell`, not inside a new section, so it stays outside the scrollable `Column` and remains visible at every scroll position (preserving the one part of the old `navigation-shell` requirement that's still correct: the control must be visible regardless of scroll position).

## Risks / Trade-offs

- [Risk] Tracking section offsets via `onGloballyPositioned` adds a small amount of recomposition/state coupling between the shell and sections it previously didn't need to know about. → Mitigation: keep the position-reporting mechanism generic (e.g. a callback each section wraps itself in from the shell, not something each section file needs to opt into individually) so `navigation-shell`'s "each section owns a single, isolated file" requirement still holds — sections don't need to change.
- [Risk] `animateScrollTo` smoothness/availability may differ slightly across Compose Multiplatform targets (Web vs Desktop vs Android). → Mitigation: the proposal already scopes smooth scroll as web-primary behavior; other targets can use immediate/default scroll animation without it reading as broken.

## Open Questions

- Should the active-section indicator be a color/weight change on the label, an underline, or both? Left to implementation to match existing `PortfolioTheme` patterns (e.g. how `LanguageSwitcher` already indicates the active language).
