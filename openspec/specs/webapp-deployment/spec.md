# webapp-deployment Specification

## Purpose
Keep `https://cirofeliu.es` serving the current `:webApp` Wasm build with no manual step: every push to `master` is tested, built and published to Ciro's self-hosted server behind Cloudflare, using a minimally scoped deploy credential and an origin that only Cloudflare can reach.

## Requirements

### Requirement: Push to master triggers an automated deploy
The system SHALL run a CI workflow whenever a commit is pushed to `master`, building the `:webApp` `wasmJs` production distribution and publishing it to `cirofeliu.es` without any manual step.

#### Scenario: PR merged to master
- **WHEN** a pull request is merged into `master`
- **THEN** the CI workflow runs automatically and, on success, the updated build is live at `https://cirofeliu.es` with no further action required

#### Scenario: Push to a non-master branch
- **WHEN** a commit is pushed to any branch other than `master`
- **THEN** the deploy workflow does not run

### Requirement: Deploy pipeline validates before publishing
The system SHALL run the project's test suite as part of the deploy workflow and SHALL NOT publish a build if any required step (tests, production build) fails.

#### Scenario: Tests fail on master
- **WHEN** `./gradlew test` fails during the deploy workflow
- **THEN** the workflow stops before transferring any files, and the previously published build remains live unchanged

#### Scenario: Build succeeds
- **WHEN** tests and the `wasmJs` production build both succeed
- **THEN** the workflow transfers the new build output to the server, replacing the previously published version

### Requirement: Deploy credential is scoped to a single writable path
The system SHALL use a dedicated SSH key for deployment, distinct from any other credential (including the existing read-only exploration key and any personal key), authorized server-side only to write to the one directory the deploy targets.

#### Scenario: Deploy key used for anything else
- **WHEN** the deploy SSH key is presented for any command other than the deploy transfer to its designated path
- **THEN** the server rejects the command

### Requirement: Static build served over HTTPS at cirofeliu.es
The system SHALL serve the published `wasmJs` static output at `https://cirofeliu.es` over TLS, with no server-side routing beyond serving static files, since the portfolio is a single scrollable page with no URL-based navigation.

#### Scenario: Visitor loads the site
- **WHEN** a visitor navigates to `https://cirofeliu.es`
- **THEN** they receive the current published build over a valid TLS connection, with the standard security headers already used on the server's other domains

### Requirement: www subdomain redirects to the root domain
The system SHALL redirect `https://www.cirofeliu.es` to `https://cirofeliu.es`, never serving content directly from the `www` host.

#### Scenario: Visitor uses the www prefix
- **WHEN** a visitor navigates to `https://www.cirofeliu.es` (or any path under it)
- **THEN** they are redirected to the equivalent `https://cirofeliu.es` URL

### Requirement: Unhashed files are revalidated on every use
The system SHALL serve every published file whose name does not carry a content hash, including the HTML pages, the entry script, stylesheets, the favicon, the social preview image, the Compose resources and every file under `blog/`, with `Cache-Control: no-cache`, together with a validator (`ETag` or `Last-Modified`) so revalidation can answer `304 Not Modified`.

#### Scenario: Entry script after a deploy
- **WHEN** a visitor who loaded the site before a deploy loads it again after the deploy
- **THEN** their browser revalidates `webApp.js` with the origin and runs the newly deployed version, not the copy it had stored

#### Scenario: Unchanged file revalidated
- **WHEN** a browser revalidates `styles.css` and the file has not changed since its stored copy
- **THEN** the response is `304 Not Modified` with no body

#### Scenario: Blog page
- **WHEN** a visitor requests `/blog/` or any post page, feed, stylesheet, font or media file under `/blog/`
- **THEN** the response carries `Cache-Control: no-cache`

### Requirement: Hashed Wasm binaries are cached as immutable
The system SHALL serve every published `.wasm` file, whose name is derived from its content, with `Cache-Control: public, max-age=31536000, immutable`.

#### Scenario: Wasm binary requested
- **WHEN** a browser requests a `.wasm` file referenced by the deployed `webApp.js`
- **THEN** the response carries `Cache-Control: public, max-age=31536000, immutable` and `Content-Type: application/wasm`

### Requirement: CDN does not override origin cache headers
The system SHALL configure the Cloudflare zone so that the `Cache-Control` value a browser receives is the one the origin sent, never a browser cache lifetime imposed by Cloudflare.

#### Scenario: Unhashed file through Cloudflare
- **WHEN** a visitor requests `https://cirofeliu.es/webApp.js` through Cloudflare
- **THEN** the response carries `Cache-Control: no-cache` and no `max-age` added by Cloudflare

### Requirement: Publication is atomic for visitors
The deploy SHALL put all new and changed files in place together at the end of the transfer and SHALL delete obsolete files only after every new file is in place, so no request during a deploy receives an entry script whose Wasm binaries are missing.

#### Scenario: Wasm binaries change in a deploy
- **WHEN** a deploy replaces the `.wasm` files with new ones under new names
- **THEN** the old `.wasm` files remain available until the new `webApp.js` and the new `.wasm` files are all in place, and are removed afterwards

### Requirement: CDN cache is purged after each deploy
The deploy workflow SHALL purge the entire Cloudflare cache of the `cirofeliu.es` zone after the files are published, using an API token whose only permission is cache purge on that zone, and SHALL fail the run if the purge is not confirmed.

#### Scenario: Successful deploy
- **WHEN** the files are published to the server
- **THEN** the workflow purges the zone's cache and Cloudflare confirms the purge

#### Scenario: Purge rejected
- **WHEN** Cloudflare rejects the purge request or does not report success
- **THEN** the workflow run fails, so the stale-cache risk is visible in CI

#### Scenario: Publication failed
- **WHEN** the file transfer to the server fails
- **THEN** the purge step does not run and the cached version of the previous deploy stays in place

### Requirement: Deploy verifies the live site loads
The deploy workflow SHALL, after purging, fetch the live `webApp.js` from `https://cirofeliu.es` and request every `.wasm` file it references, and SHALL fail the run if any of those requests does not succeed or if the script references no `.wasm` file.

#### Scenario: Live site consistent
- **WHEN** the live `webApp.js` and every `.wasm` it references respond with `200`
- **THEN** the verification step succeeds

#### Scenario: Referenced binary missing
- **WHEN** the live `webApp.js` references a `.wasm` file that responds with `404`
- **THEN** the workflow run fails and names the missing file
