Nexus is [Ciro Feliu's](https://github.com/CiroFeliu) personal portfolio (Senior Android Developer / Mobile Systems Architect), built as a Kotlin Multiplatform + Compose Multiplatform single-page app (package `app.luxion.nexus`). One shared UI drives four targets so the portfolio behaves consistently everywhere:

- **[`:webApp`](./webApp)** — the primary target: Kotlin/Wasm, with a JS fallback for browsers without WasmGC. Live at [cirofeliu.es](https://cirofeliu.es), auto-published on push to `master`.
- **[`:androidApp`](./androidApp)**, **[`:iosApp`](./iosApp)**, **[`:desktopApp`](./desktopApp)** — demonstrate the same portfolio's multiplatform reach.

## Project layout

- [`/shared`](./shared/src) contains all portfolio UI, theming, and content, shared across every target.
  - [`commonMain`](./shared/src/commonMain/kotlin) holds the Compose UI and section content common to all targets.
  - Other source sets (`androidMain`, `iosMain`, `jvmMain`, `jsMain`, `wasmJsMain`) hold only `expect`/`actual` platform glue (e.g. CV export, opening a URL).
- Each platform module (`webApp`, `androidApp`, `iosApp`, `desktopApp`) wires up its own entry point and renders `:shared`'s UI — no portfolio content lives there.
- [`/blogGenerator`](./blogGenerator) is a build-time Kotlin/JVM tool that generates the static blog from [`/blog`](./blog); it is never shipped to the browser.

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

## Blog

[`/blog`](./blog) holds the posts published at [cirofeliu.es/blog/](https://cirofeliu.es/blog/), one Markdown folder per post (see [`blog/README.md`](./blog/README.md) for the authoring format). [`:blogGenerator`](./blogGenerator) is a Kotlin/JVM module that turns them into static HTML pages and an RSS feed; `:webApp`'s `generateBlog` task runs it and adds the result to the web resources, so the distribution and the dev server serve `/blog/` as plain files. The Compose navigation links to it.

```bash
./gradlew :webApp:wasmJsBrowserDevelopmentRun -Pblog.drafts=true   # preview, drafts included
./gradlew :blogGenerator:test                                      # generator tests
```

## Deployment

Merging to `master` publishes the web app automatically via [`.github/workflows/deploy.yml`](./.github/workflows/deploy.yml):

1. `./gradlew test` — a failing test stops the run before anything is published.
2. `./gradlew :webApp:wasmJsBrowserDistribution`
3. `rsync --delete` of `webApp/build/dist/wasmJs/productionExecutable/` (source maps excluded) to the server. Changed files are moved into place together at the end of the transfer and obsolete ones are deleted only afterwards, so a visitor never gets a `webApp.js` whose `.wasm` files are gone.
4. Purge of the whole Cloudflare cache of the `cirofeliu.es` zone.
5. Check of the live site: `webApp.js` must be served with `Cache-Control: no-cache` and every `.wasm` it references must load.

The server sends `Cache-Control: public, max-age=31536000, immutable` for the `.wasm` files, whose names are content hashes, and `Cache-Control: no-cache` for everything else (HTML, `webApp.js`, stylesheets, resources, the blog), and Cloudflare is set to respect those headers. Only content-hashed files may be cached long.

The workflow can also be triggered manually from the Actions tab (`workflow_dispatch`). It needs these repository secrets:

| Secret | Purpose |
|---|---|
| `NEXUS_DEPLOY_SSH_KEY` | Private half of the deploy-only SSH key |
| `NEXUS_DEPLOY_KNOWN_HOSTS` | Pinned server host keys (strict host key checking) |
| `NEXUS_DEPLOY_HOST` / `NEXUS_DEPLOY_PORT` / `NEXUS_DEPLOY_USER` | SSH connection details |
| `NEXUS_CLOUDFLARE_PURGE_TOKEN` | Cloudflare API token limited to cache purge on the `cirofeliu.es` zone |
| `NEXUS_CLOUDFLARE_ZONE_ID` | Identifier of the `cirofeliu.es` zone |

The deploy key can only write into the site directory on the server (`rrsync -wo`); it cannot open a shell, read files, or forward ports. To roll back, revert the offending commit on `master` — the workflow redeploys the previous build.

## Contributing

Code style and process guidelines for anyone (human or AI agent) working in this repo live in [`AGENTS.md`](./AGENTS.md). Non-trivial changes go through an [OpenSpec](https://github.com/Fission-AI/OpenSpec) proposal under [`openspec/changes/`](./openspec/changes) before implementation.

## License

[MIT](./LICENSE)
