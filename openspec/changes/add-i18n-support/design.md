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
- **Persistence: hand-rolled `expect`/`actual` storage, not `multiplatform-settings`.** Resolved at implementation time: neither `kotlinx.browser` (Kotlin/JS stdlib, `js`-target only) nor the project's existing `kotlin-wrappers:kotlin-browser` dependency binds the Web Storage API for the `wasmJs` target, so adding `multiplatform-settings` wouldn't remove the need to hand-write Web storage access anyway — it would only add an unverified external dependency on top of it. Went straight to `expect fun readPersistedLanguage(): Language?` / `expect fun persistLanguage(language: Language)`, one `actual` per target: `SharedPreferences` (Android, via the existing `AndroidAppContext`), `NSUserDefaults` (iOS), `java.util.prefs.Preferences` (Desktop/JVM), `kotlinx.browser.localStorage` (Web/JS), and a small `js("localStorage...")` snippet (Web/Wasm, using Kotlin/Wasm's JS-interop intrinsic since no typed binding exists there). Verified by compiling `:shared` for every target but Android (blocked by no `ANDROID_HOME` in this environment).
- **Language-switcher placement: a small control row in `PortfolioShell`, not a new navigation system.** The portfolio deliberately has no section-anchor navigation (single scrolling page, by design). The switcher is a narrow, persistent utility control — not a nav bar — analogous in spirit to a dark-mode toggle, so it doesn't reintroduce the navigation surface that was explicitly ruled out.

## Risks / Trade-offs

- [Translating five sections' worth of content by hand risks translation drift or inconsistent tone between English/Spanish] → Mitigation: keep both language variants of a section's content co-located in the same file (`Map<Language, XContent>`), so a content edit is a visible, single-file diff in both languages at once rather than two separate edits that can silently diverge.
- [Hand-rolled `expect`/`actual` persistence means five near-duplicate storage implementations to keep correct, instead of one shared library call] → Acceptable: each `actual` is a few lines wrapping a platform API that's already idiomatic for the platform (`SharedPreferences`, `NSUserDefaults`, `java.util.prefs`, `localStorage`), and the surface (`readPersistedLanguage`/`persistLanguage`) is small and unlikely to grow.
- [A persistent global `CompositionLocal` for language is a new architectural pattern not present elsewhere in the codebase] → Acceptable: it directly mirrors the existing `MaterialTheme`-provided-locals pattern already used for theming, so it doesn't introduce an unfamiliar idiom.

## Open Questions

- Default language when the system locale is genuinely ambiguous or unavailable (e.g. some embedded/CI environments) — proposed default is English; confirmed during implementation, no change needed (`Language.fromTag` falls back to `Language.Default` = English for any unmatched tag).
