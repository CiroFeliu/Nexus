## 1. Language model and app state

- [x] 1.1 Define `Language` enum (English, Spanish) in `:shared`
- [x] 1.2 Declare `expect fun systemLanguageTag(): String` in `:shared`, with `actual` implementations for Android, iOS, Desktop, and Web (Wasm + JS)
- [x] 1.3 Resolve `systemLanguageTag()` to a `Language`, defaulting to English for unsupported tags
- [x] 1.4 Add `LocalAppLanguage` `CompositionLocal`, provided from `App()`

## 2. Persistence

- [x] 2.1 Evaluate `multiplatform-settings` (russhwolf) for this project's Gradle/KMP setup; confirm or fall back to a hand-rolled `expect`/`actual` store (see design.md Open Questions)
- [x] 2.2 Implement read/write of the persisted language override across all four targets
- [x] 2.3 Wire `App()` startup resolution: persisted override, else system locale, else English

## 3. Language-switcher control

- [x] 3.1 Build the manual language-switcher composable (English/Spanish selection)
- [x] 3.2 Mount it in `PortfolioShell`, visible regardless of scroll position
- [x] 3.3 Wire selection to update `LocalAppLanguage` state and persist the override

## 4. Translate section content

- [x] 4.1 Hero/About: convert `NAME`/`ROLE`/`BIO` consts to `Language`-keyed content, add Spanish translation
- [x] 4.2 Skills: convert category labels to `Language`-keyed content, add Spanish translation
- [x] 4.3 Experience: convert entry role titles/descriptions to `Language`-keyed content, add Spanish translation
- [x] 4.4 Projects: convert card titles/descriptions to `Language`-keyed content, add Spanish translation
- [x] 4.5 Contact: convert user-facing labels to `Language`-keyed content, add Spanish translation (URLs unaffected)

## 5. Verification

- [ ] 5.1 Verify startup language resolution on all four targets (system locale in English, Spanish, and an unsupported locale)
- [x] 5.2 Verify manual override switches all five sections' content immediately, with no page reload
- [ ] 5.3 Verify manual override persists across an app restart on all four targets
- [x] 5.4 Verify no hardcoded single-language string remains in any section file
