## Context

`shared/build.gradle.kts` already declares a `commonTest` source set with `kotlin.test` as a dependency (part of the original KMP project scaffold), but no `commonTest/kotlin` directory or test files exist yet, and no Compose UI testing artifact is on the classpath. `:shared` currently holds: the `PortfolioSection` enum/registry, `PortfolioShell`, five section `Content()` composables, and `PortfolioTheme`.

This change is being proposed alongside `add-i18n-support` and `add-dynamic-cv-export`, intended for parallel implementation — both of those will edit the same section files this change adds tests for.

## Goals / Non-Goals

**Goals:**
- Establish a working `commonTest` convention in `:shared` that other changes can extend.
- Cover the structural/regression-prone surface: section registry integrity, shell composition, each section rendering without error.
- Keep assertions resilient to content that's expected to change soon (translated strings from `add-i18n-support`), by asserting structure (node counts, presence of stable identifiers) rather than exact English copy wherever the two risk colliding.

**Non-Goals:**
- No CI/GitHub Actions setup — out of scope; this change only makes `./gradlew test` meaningful locally. (Deployment/CI infrastructure is out of scope for the project per `AGENTS.md`.)
- No testing of platform `actual` implementations (e.g. `openUrl`) — these call real platform APIs and aren't practical to unit test; manual verification remains the mechanism for those, as it already is today.
- No visual regression / screenshot testing — out of scope for this change.
- No coverage tooling (e.g. Kover) setup — pass/fail test execution only, for now.

## Decisions

- **`kotlin.test` for pure logic, Compose Multiplatform `runComposeUiTest` for composable smoke tests.** `kotlin.test` is already on the classpath for `commonTest`; `runComposeUiTest` (from `org.jetbrains.compose.ui:ui-test`) is the standard way to render and assert on composables from common code without a real Android/iOS device, and runs on the JVM (and other targets that support it) as part of `./gradlew test`. Add the corresponding test dependency to `commonTest` in `shared/build.gradle.kts`.
- **Assert structure over exact copy.** For section smoke tests, prefer asserting things unlikely to change with i18n (e.g. "Hero/About renders an avatar and 3 text nodes", "Skills section renders N category groups") over the literal English sentences, so this change and `add-i18n-support` don't produce conflicting test expectations when both land. Where a specific string is a reasonable stable anchor (e.g. the name "Ciro Feliu" isn't translated), asserting on it directly is fine.
- **One test file per production file.** Mirrors the existing one-section-per-file convention: `PortfolioSectionTest.kt`, `PortfolioShellTest.kt`, and one test file per section (`HeroAboutSectionTest.kt`, etc.), all under `shared/src/commonTest/kotlin/app/luxion/nexus/`, mirroring the production package structure.

## Risks / Trade-offs

- [Tests written now may need small updates once `add-i18n-support` lands and section content becomes language-keyed] → Mitigation: the structure-over-copy assertion strategy above minimizes the blast radius; any remaining breakage is expected, bounded, and easy to fix (update an assertion, not a rewrite).
- [`runComposeUiTest` is still evolving across Compose Multiplatform versions] → Mitigation: scope its use to smoke tests only (renders without throwing, key nodes present) rather than deep interaction testing, which is the most stable subset of that API today.
