## ADDED Requirements

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
