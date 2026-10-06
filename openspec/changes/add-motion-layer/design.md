## Context

The page is a non-lazy `Column` inside `verticalScroll`, so every section composes at startup; "enter viewport" cannot rely on lazy composition. Motion tokens and `LocalReducedMotion` come from `refine-design-foundation`.

## Goals / Non-Goals

**Goals:**
- Every animation has a reason (hierarchy, sequence, feedback, state).
- Smooth 60fps on the web target; no recomposition per frame.
- Full parity of content with motion disabled.

**Non-Goals:**
- Scroll hijacking, pinned/stacked sections, horizontal scroll sections.
- Page transitions (single page).
- Perpetual decorative loops (the optional skills marquee is owned by `overhaul-section-layouts`).

## Decisions

- **Phase discipline.** All animated values are read inside `graphicsLayer {}`, `Modifier.offset {}` or draw lambdas. Scroll-linked values read `scrollState.value` inside those lambdas, never copied into `mutableStateOf`.
- **Reveal trigger.** Use `Modifier.onFirstVisible` / `onVisibilityChanged` if available in the project's Compose version; otherwise a small `Modifier.revealWhenVisible(scrollState)` comparing `onGloballyPositioned` bounds with the viewport height. "Revealed" flags are hoisted per section key in the shell so reveals never replay. Initial state is visible when reduced motion is on, so nothing starts hidden.
- **Timing.** Entry: 600ms, `CubicBezierEasing(0.16f, 1f, 0.3f, 1f)`, 16-24dp rise, 60ms stagger, max ~5 staggered children. Feedback: springs (`StiffnessMediumLow`, no bounce). Nav underline: spring on offset and width.
- **Spotlight border.** `pointerInput` tracks the hover position into a `mutableStateOf<Offset>` read only inside `drawWithCache`/`drawBehind`, drawing a radial `Brush` in the accent color clipped to the tile border. Only on hover-capable pointers; absent on touch.
- **Parallax.** Portrait `translationY = scroll * 0.15f`, clamped to the hero's height so it never detaches; disabled on compact and under reduced motion.
- **Reduced motion.** A single `rememberMotionSpec()` helper returns `snap()` specs when `LocalReducedMotion` is true; parallax and spotlight are not applied at all.

## Risks / Trade-offs

- [Risk] Visibility detection via `onGloballyPositioned` runs layout callbacks during scroll. → Callbacks only flip a boolean once per section; after reveal the modifier becomes a no-op.
- [Risk] Too much motion undermines the "clean, technical" tone. → Motion is limited to the list above; review on the branch before archiving, and drop to `MOTION_INTENSITY 4` (hero entry + hover only) if it feels busy.

## Open Questions

- Whether Compose `onFirstVisible` behaves correctly inside a `verticalScroll` on Wasm (verify before choosing the custom fallback).
