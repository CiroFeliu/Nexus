## Context

`PortfolioShell` stacks `SectionNavigation` (a `FlowRow` of `TextButton`s) and `LanguageSwitcher` (a right-aligned `Row`) above a `verticalScroll` column. Section offsets are tracked with `onSizeChanged` and `animateScrollTo` is already used for section jumps. `HeroAboutSection.Content(photo)` renders a centered column.

This is the **evolve** variant; the **overhaul** variant lives on `feature/improve-design-overhaul` and must not share files with this change beyond the common foundation.

## Foundation available

Built on the archived `refine-design-foundation`, `polish-portfolio-copy` and `improve-web-shell` changes. Use their pieces instead of re-creating them: `PortfolioContentContainer`, `LocalWidthClass`/`WidthClass`, `PortfolioLayout`, `PortfolioMotion` + `motionSpec()`, `LocalReducedMotion`, `Modifier.interactive(...)`, `LocalPortfolioMonoTypography`, `PortfolioShapeRoles`, and the shell's `notifyAppReady()`/`applyDocumentLanguage()` bridge.

## Goals / Non-Goals

**Goals:**
- One navigation bar, one line, at every width.
- A hero that states who Ciro is and offers a next step within the first viewport.
- Same section order, ids and nav labels as today.

**Non-Goals:**
- Changing other sections (`evolve-section-layouts`).
- Scroll-linked effects, parallax or pinned sections (overhaul territory).
- A logo/monogram mark.

## Decisions

- **Nav structure.** `PortfolioTopBar(activeSection, onSectionSelected, onLanguageSelected)` replaces the two composables in the shell. Width decides the layout via `PortfolioLayout` breakpoints: medium+ shows inline links; compact shows wordmark, language toggle and a menu button opening an M3 `DropdownMenu` with the links. `LanguageSwitcher` becomes a compact segmented `EN | ES` toggle reused inside the bar (the `i18n-support` "visible regardless of scroll" requirement stays satisfied because the bar is outside the scroll container).
- **Active indicator.** A 2dp accent underline drawn under the active link; its x-offset and width animate with the motion tokens (instant under reduced motion). Link positions come from `onGloballyPositioned` inside the bar. Active link text uses `onSurface`, inactive `onSurfaceVariant`; no bold toggling (avoids width jumps).
- **Scroll hairline.** The bar's bottom `outlineVariant` hairline alpha derives from `scrollState.value > 0` via `derivedStateOf`, so it does not recompose per scroll frame.
- **Hero layout.** Medium+: `Row` with text column `weight(1.3f)` and photo `weight(1f)` capped at ~360dp, vertically centered, inside `PortfolioContentContainer`. Compact: `Column`, photo 120dp, all left-aligned. Photo uses `MaterialTheme.shapes.large` and `ContentScale.Crop` with a fixed aspect ratio (4:5) so space is reserved before the resource loads on web. The initials fallback keeps the same shape and size.
- **Hero type.** Name `displayLarge` (Geist, tight tracking), role `titleLarge` in `onSurfaceVariant`, bio `bodyLarge` capped at `readableTextWidth`.
- **Hero actions.** Primary `Button` "Download CV" calling the existing `exportCvToPdf(buildCvContent(language))`; secondary `TextButton` "Contact" that calls the shell's scroll-to-section lambda for `PortfolioSection.Contact`. The lambda is passed down through `PortfolioSection.content` so the section file does not reach into the shell (keeps `navigation-shell` isolation).
- **Entry motion.** Hero text and photo fade in and rise 16dp, staggered 60ms, once per app start; `LocalReducedMotion` skips it.

## Risks / Trade-offs

- [Risk] `PortfolioSection.content` is `@Composable () -> Unit`; passing a scroll callback changes its signature for every section. → Introduce a small `SectionScope`/callbacks parameter used only by the hero; other sections ignore it.
- [Risk] Compact menu adds a component not present today. → Standard M3 `DropdownMenu`, covered by a UI test opening it and selecting a section.
- [Trade-off] Cutting the bio to 20 words loses detail; the experience and projects sections carry it.

## Copy

- **Hero bio (draft, Ciro reviews on the branch):**
  - EN: "I design and build mobile systems that stay maintainable as they grow, from Android architecture to Kotlin Multiplatform."
  - ES: "Diseño y construyo sistemas móviles que siguen siendo fáciles de mantener al crecer, de la arquitectura Android a Kotlin Multiplatform."

## Open Questions

- None blocking.
