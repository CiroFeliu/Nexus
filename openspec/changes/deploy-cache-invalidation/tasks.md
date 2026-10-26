# Tasks

## 1. Server and Cloudflare setup (manual, Ciro)

- [ ] 1.1 Create a Cloudflare API token with only the `Zone > Cache Purge > Purge` permission, restricted to the `cirofeliu.es` zone, and store it and the zone identifier as repository secrets `NEXUS_CLOUDFLARE_PURGE_TOKEN` and `NEXUS_CLOUDFLARE_ZONE_ID`; verify with `gh secret list` that both names exist
- [ ] 1.2 Add to the server's Caddy site block for `cirofeliu.es` a `Cache-Control: public, max-age=31536000, immutable` header for `*.wasm` and `Cache-Control: no-cache` for every other path, reload Caddy and record the change in the server's private docs (never in this repo); verify with `curl -I` against the origin that `/webApp.js` and `/blog/` return `no-cache` and a `.wasm` returns the immutable value
- [ ] 1.3 Set the `cirofeliu.es` zone's Browser Cache TTL to "Respect Existing Headers" and purge the zone once from the dashboard; verify with `curl -I https://cirofeliu.es/webApp.js` that the response through Cloudflare carries `cache-control: no-cache` and no `max-age=14400`

## 2. Atomic publication

- [ ] 2.1 Add `--delay-updates --delete-after` to the `rsync` call in `.github/workflows/deploy.yml`, keeping the existing options; verify locally with `rsync -n` (dry run) between two directories that the option set is accepted, and that the options are in the server's `rrsync` allow-list

## 3. Cloudflare purge step

- [ ] 3.1 Add a "Purge Cloudflare cache" step after "Publish to server" that posts `{"purge_everything":true}` to the zone's `purge_cache` endpoint with `NEXUS_CLOUDFLARE_PURGE_TOKEN` and `NEXUS_CLOUDFLARE_ZONE_ID` from the environment, retries transient failures and fails unless the response has `"success": true`; verify the step's script locally against a recorded success and failure response, and the workflow with `actionlint` if available

## 4. Post-deploy check

- [ ] 4.1 Add a "Verify live site" step after the purge that downloads `https://cirofeliu.es/webApp.js`, fails if it is not served with `no-cache` or references no `[0-9a-f]{20}.wasm`, requests every referenced `.wasm` and fails naming any that does not return `200`; verify by running the script locally against the live site (pass) and against a copy of `webApp.js` with an altered `.wasm` name (fails naming it)

## 5. Documentation

- [ ] 5.1 Update the deployment summary in `AGENTS.md` (and `README.md` if it describes the deploy) to mention the cache policy and the post-deploy purge and check at the same high level, with no server details; verify with `git diff` that no host, path, IP or Caddy config was added

## 6. Integration

- [ ] 6.1 Run `openspec validate deploy-cache-invalidation --strict` and `./gradlew test`, and review `git diff origin/develop...HEAD` for anything that must not be published in a public repo

## Workflow follow-up

- Archive the change in the same branch before opening the PR against `develop`, assigned to CiroFeliu.
- After the release reaches `master`, confirm in the deploy run that the purge reported success and the live-site check passed, and check `curl -I` on `/`, `/webApp.js`, `/styles.css`, `/blog/` and one `.wasm`.
