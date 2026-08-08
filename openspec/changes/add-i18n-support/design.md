## Context

Every section today is an `object` holding `const val String` fields (see `HeroAboutSection.NAME`, `.ROLE`, `.BIO`, and the equivalent pattern in Skills/Experience/Projects/Contact) — single-language, compiled-in English text with no indirection for locale. There is no existing app-wide state container (no ViewModel-equivalent, no `CompositionLocal` beyond what `MaterialTheme` provides), and no cross-session persistence mechanism anywhere in the codebase yet.

## Goals / Non-Goals

**Goals:**
- Detect the visitor's system locale on startup and default to Spanish or English accordingly (fallback to English for any other locale).
- Let a visitor override the detected language manually, from a control visible regardless of scroll position (not a per-section control).
- Persist a manual override across sessions/app restarts, on all four targets.
- Translate all five sections' content into both languages, with no section left English-only.

**Non-Goals:**
- No support for languages beyond English/Spanish in this change — the `Language` model should be easy to extend later, but only two languages ship now.
- No per-section language override — language is a single, app-wide setting.
- No translation of build/dev tooling output (Gradle task names, READMEs) — this is portfolio *content* only.

## Decisions

- **`Language` as a shared enum, not Compose String Resources.** The portfolio's content isn't flat UI labels — it's structured data (an Experience entry has company/role/dates/description; a Project entry has title/description/tech/links). Modeling each section's content as `data class XContent(...)` with one instance per `Language`, held in a `Map<Language, XContent>` per section, keeps structure and translation together and stays consistent with the existing per-section `object` pattern (each section still owns its own content file, just keyed by language instead of a flat constant). Compose Multiplatform's resource system is a better fit for image/font assets than for this kind of structured, per-entry content, so it's not adopted here.
- **App-wide language state via `CompositionLocal`.** `App()` resolves the initial `Language` (persisted override, else system locale, else English) once at startup and holds it in a `mutableStateOf<Language>`, provided to the whole tree via a `LocalAppLanguage` composition local — mirroring how `MaterialTheme` already provides theme values app-wide. Every section reads `LocalAppLanguage.current` to pick its content variant.
- **System locale detection via `expect`/`actual`.** `expect fun systemLanguageTag(): String` resolved per platform: `Locale.getDefault()` (Android, Desktop/JVM), `NSLocale.currentLocale` (iOS), `window.navigator.language` (Web, both Wasm and JS actuals). Mapped to `Language.English` or `Language.Spanish` by matching the leading `es`/`en` tag; anything else falls back to English.
- **Persistence: evaluate `multiplatform-settings` (russhwolf) before hand-rolling `expect`/`actual` storage.** This is a well-established KMP library providing a single `Settings` API backed by `SharedPreferences` (Android), `NSUserDefaults` (iOS), Java `Preferences` (Desktop/JVM), and browser `localStorage` (Web) — exactly the four targets this project needs, with no per-platform storage code to maintain. Fallback if it doesn't fit cleanly: a minimal `expect class LanguageStore { fun read(): String?; fun write(tag: String) }` with one `actual` per target. Final call made at implementation time (see Open Questions).
- **Language-switcher placement: a small control row in `PortfolioShell`, not a new navigation system.** The portfolio deliberately has no section-anchor navigation (single scrolling page, by design). The switcher is a narrow, persistent utility control — not a nav bar — analogous in spirit to a dark-mode toggle, so it doesn't reintroduce the navigation surface that was explicitly ruled out.

## Risks / Trade-offs

- [Translating five sections' worth of content by hand risks translation drift or inconsistent tone between English/Spanish] → Mitigation: keep both language variants of a section's content co-located in the same file (`Map<Language, XContent>`), so a content edit is a visible, single-file diff in both languages at once rather than two separate edits that can silently diverge.
- [`multiplatform-settings` adds a new external dependency across all four targets] → Mitigation: it's a widely-used, actively maintained KMP library scoped to exactly this problem; if it doesn't integrate cleanly with this project's Gradle setup, the `expect`/`actual` fallback is a bounded, well-understood amount of code (one storage call per platform).
- [A persistent global `CompositionLocal` for language is a new architectural pattern not present elsewhere in the codebase] → Acceptable: it directly mirrors the existing `MaterialTheme`-provided-locals pattern already used for theming, so it doesn't introduce an unfamiliar idiom.

## Open Questions

- Confirm `multiplatform-settings` integrates cleanly with this project's current Gradle/KMP version before committing to it over the `expect`/`actual` fallback — resolve at the start of implementation.
- Default language when the system locale is genuinely ambiguous or unavailable (e.g. some embedded/CI environments) — proposed default is English; confirm during implementation if this needs revisiting.
