## Why

Nexus's `:webApp` has no way to reach a visitor yet — there is no hosting, no domain wiring, and no publish step. Ciro already runs other domains on his own server (Docker + Caddy + Cloudflare), and wants Nexus self-hosted the same way under `cirofeliu.es`, published automatically whenever `master` moves, instead of a manual copy step per release.

## What Changes

- Add a GitHub Actions workflow, triggered on push to `master`, that builds the `:webApp` `wasmJs` production distribution and ships the static output to Ciro's server over SSH, using a new deploy-only SSH key scoped to nothing but this task (distinct from the existing read-only exploration key and from Ciro's personal key).
- Add a new Docker Compose + Caddyfile directory on the server, modeled on an existing reverse-proxy pattern already running there: Caddy serves the static `wasmJs` build for `cirofeliu.es` over TLS, with the same security headers (HSTS, X-Content-Type-Options, X-Frame-Options, Referrer-Policy, Permissions-Policy) already standard on that server.
- Configure the `cirofeliu.es` Cloudflare zone: a proxied A record pointing at the server, kept current by the DDNS mechanism already running there; TLS via a Cloudflare Origin Certificate with Full (strict) SSL, matching the server's existing convention.
- One-time manual step (not automated by this change): open `80/tcp` and `443/tcp` in UFW for this domain's traffic, since firewall changes are a deliberate action outside CI's reach.

**BREAKING**: none — purely additive infrastructure; no existing app behavior changes.

## Capabilities

### New Capabilities
- `webapp-deployment`: the publish pipeline from a `master` push to a live static site at `cirofeliu.es` — CI build/deploy trigger, transfer mechanism, and the server-side serving contract (domain, TLS, static-file hosting; no backend, no SPA-routing rewrites needed since `navigation-shell` is a single scrollable page).

### Modified Capabilities
_None — no existing spec's requirements change; this only adds a publish path for what those specs already describe._

## Impact

- New file in this repo: `.github/workflows/deploy.yml`.
- New GitHub repo secrets: deploy SSH private key, server host/path.
- New files on Ciro's server (outside this repo; specific paths documented privately, not in this repo): a Compose file and Caddyfile for the new deploy target.
- New Cloudflare DNS record + Origin Certificate for `cirofeliu.es`.
- New deploy-only SSH key pair, separate from the existing read-only exploration key.
