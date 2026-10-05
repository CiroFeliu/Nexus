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
