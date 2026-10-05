## Context

`HeroAboutSection.Avatar()` today unconditionally renders initials in a colored circle — there's no branch, no image loading, no asset pipeline wired up. `PortfolioShell` renders each `PortfolioSection.content()` back to back inside one `Column`, each section currently choosing its own padding but none setting an explicit background, so they all inherit the same `Surface` color set once in `App.kt`.

## Goals / Non-Goals

**Goals:**
- A real photo, when supplied, replaces the initials avatar without any code change beyond adding the asset file.
- Visually distinguishable section boundaries using only existing theme tokens.
- Works identically across all four targets (no platform-specific image loading code).

**Non-Goals:**
- No image cropping/upload UI — the photo is a static bundled asset, not user-configurable.
- No redesign of section internals (typography, spacing, card styles) — only background/surface alternation.
- No large hero background imagery, gradients, or illustrations — that's a bigger visual redesign outside this change's scope.

## Decisions

- **Photo delivery**: use `compose.components.resources` (`painterResource`) against a file under `shared/src/commonMain/composeResources/drawable/`. This is the officially supported cross-platform image mechanism for Compose Multiplatform and already resolves for Wasm, JS, Android, iOS, and Desktop with a single API — no third-party image-loading library needed since the photo is a static bundled asset, not fetched from a URL.
- **Fallback logic**: `Avatar()` takes an optional photo `Painter?` (or resource id) — null falls through to the current initials rendering. This keeps the existing "no photo asset supplied yet" scenario true by construction rather than by a manual flag.
- **Section rhythm**: alternate `background` → `surface` → `background` ... across the five sections in `PortfolioSection` order, driven from `PortfolioShell` (one place decides which token each section gets) rather than each section file hardcoding its own choice — keeps `navigation-shell`'s "each section owns a single, isolated file" requirement intact, since sections don't need to know about their neighbors.
- **Contrast**: `surfaceVariant` is intentionally not used for full-section backgrounds (it's already used for chip/tag backgrounds inside sections per `SkillsSection`/`ProjectsSection`) — reusing it at section scale would clash with those smaller elements. Alternating strictly between `background` and `surface` avoids that collision.

## Risks / Trade-offs

- [Risk] `background` and `surface` are defined identically today in both `PortfolioLightColorScheme` and `PortfolioDarkColorScheme` (see `Color.kt`), so alternating between them currently produces **zero visible difference**. → Mitigation: this change must also give `surface` a distinct-but-subtle value from `background` in `Color.kt` (small delta, not a new color relationship) for the alternation to be visible; this is a `design-system`-adjacent token tweak, not a new token.
- [Risk] No photo asset exists yet — Ciro must supply one before this change is visually complete. → Mitigation: task list calls this out explicitly as a blocking manual step; the fallback keeps the app buildable/runnable without it.

## Open Questions

- Final headshot photo file (format, crop) — to be supplied by Ciro before/during implementation.
