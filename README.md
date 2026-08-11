Nexus is [Ciro Feliu's](https://github.com/CiroFeliu) personal portfolio (Senior Android Developer / Mobile Systems Architect), built as a Kotlin Multiplatform + Compose Multiplatform single-page app (package `app.luxion.nexus`). One shared UI drives four targets so the portfolio behaves consistently everywhere:

- **[`:webApp`](./webApp)** — the primary target: Kotlin/Wasm, with a JS fallback for browsers without WasmGC. Will be self-hosted at `cirofeliu.es`, auto-published on push to `master`.
- **[`:androidApp`](./androidApp)**, **[`:iosApp`](./iosApp)**, **[`:desktopApp`](./desktopApp)** — demonstrate the same portfolio's multiplatform reach.

## Project layout

- [`/shared`](./shared/src) contains all portfolio UI, theming, and content, shared across every target.
  - [`commonMain`](./shared/src/commonMain/kotlin) holds the Compose UI and section content common to all targets.
  - Other source sets (`androidMain`, `iosMain`, `jvmMain`, `jsMain`, `wasmJsMain`) hold only `expect`/`actual` platform glue (e.g. CV export, opening a URL).
- Each platform module (`webApp`, `androidApp`, `iosApp`, `desktopApp`) wires up its own entry point and renders `:shared`'s UI — no portfolio content lives there.

### Testing

`:shared` has a `commonTest` source set covering the section registry, the shell, and every
section's `Content()` composable. Run it with:

```bash
./gradlew test
```

Conventions, mirroring the `commonMain` layout under
[`shared/src/commonTest/kotlin`](./shared/src/commonTest/kotlin):

- One test file per production file (`PortfolioSectionTest.kt`, `PortfolioShellTest.kt`, one
  per section under `navigation/sections/`).
- Plain logic (e.g. the section registry) uses `kotlin.test`; composables use Compose
  Multiplatform's `runComposeUiTest` for smoke tests — renders without throwing, expected
  structural content is present.
- Assertions favor structure and stable anchors (names, product/company names, node counts)
  over exact copy, since section text is expected to become i18n-keyed.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Web app:
  - Wasm target (faster, modern browsers): `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
  - JS target (slower, supports older browsers): `./gradlew :webApp:jsBrowserDevelopmentRun`
- Android app: `./gradlew :androidApp:assembleDebug`
- Desktop app:
  - Hot reload: `./gradlew :desktopApp:hotRun --auto`
  - Standard run: `./gradlew :desktopApp:run`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

## Contributing

Code style and process guidelines for anyone (human or AI agent) working in this repo live in [`AGENTS.md`](./AGENTS.md). Non-trivial changes go through an [OpenSpec](https://github.com/Fission-AI/OpenSpec) proposal under [`openspec/changes/`](./openspec/changes) before implementation.

## License

[MIT](./LICENSE)
