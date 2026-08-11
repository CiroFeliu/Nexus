## Context

Nexus's `:webApp` is a pure static SPA: one scrollable page, no server-side routing (`navigation-shell`), no backend or form submission (`contact-section`). Ciro runs his own self-hosted server (Ubuntu, Docker-based) that already hosts several other services behind a firewall and Fail2ban, with one existing Caddy + Cloudflare reverse-proxy example (currently not running) and a DDNS mechanism that keeps his other domains pointed at the server's current IP, all as **DNS-only (gray-cloud) records with Caddy's own automatic Let's Encrypt HTTPS** — the server has no prior example of a Cloudflare-*proxied* domain or an Origin Certificate. `cirofeliu.es` is a brand-new, separate Cloudflare zone — nothing is wired up for it yet, and this change is the first time this server serves a proxied domain. A read-only SSH key for Claude Code already exists for server exploration (documented privately, outside this repo) — it is explicitly insufficient for this: deploy needs its own separate, minimally-scoped write credential, never a reuse of the read-only one.

## Goals / Non-Goals

**Goals:**
- A push to `master` ends with the current build live at `cirofeliu.es`, no manual step beyond the one-time initial setup.
- Match the server's existing conventions (Caddy in Docker Compose, Cloudflare-managed DNS/TLS, standard security headers) instead of introducing a new pattern for this one domain.
- Keep the deploy credential minimally scoped — write access to exactly the one path it needs, nothing else.

**Non-Goals:**
- `js` fallback target — deferred; this change ships `wasmJs` only.
- Any backend or dynamic serving — output is static files only.
- Preview/staging environments or zero-downtime/blue-green deploys — v1 is a simple overwrite on each deploy.
- Automating the UFW firewall change or the initial Cloudflare zone creation — one-time manual setup, listed in `tasks.md`, performed by Ciro.

## Decisions

1. **Transfer mechanism: `rsync` over SSH, not a Docker image push.** The build output is static files; rsyncing the `wasmJs` distribution to a fixed path is simpler than building/pushing/pulling an image per deploy, and Caddy just serves that path as its `root`. Alternative considered: bake the build into a Docker image each deploy and `docker compose up -d`; rejected as unnecessary complexity for static files.
2. **Deploy credential: a brand-new, single-purpose SSH key**, authorized via a forced `command=` in `authorized_keys` running `rrsync` (rsync's own restricted-rsync contrib script, not a hand-rolled wrapper) in write-only mode, locked to the deploy target directory — analogous in spirit to the existing read-only exploration key, but scoped to one writable path instead of read-only. `rrsync` exists precisely because hand-rolling a `command=` around raw `rsync --server` is easy to get wrong (path traversal via `rsync`'s own `--rsync-path`/args); use the vetted tool instead of reinventing it. Never reuse the read-only key or Ciro's personal key for this.
3. **Server-side serving: Caddy in Docker Compose**, a new directory on the server, reusing the existing reverse-proxy example's security-header block and file layout, but *not* its automatic-HTTPS mechanism (see next decision) — everything else about the layout follows the same pattern rather than inventing a new one.
4. **TLS: Cloudflare Origin Certificate + Full (strict) SSL, not Caddy's automatic Let's Encrypt.** This is a deliberate departure from `transfer/`'s pattern, not a continuation of an existing convention (the server has never served a Cloudflare-proxied domain before): a proxied record means Cloudflare's edge terminates the public-facing TLS handshake, so an automatic ACME HTTP-01 challenge from the origin would depend on Cloudflare passing it through cleanly and would need to re-issue every 90 days. An Origin Certificate is issued once (15-year validity), requires no ACME dance behind the proxy, and is exactly what Full (strict) mode expects.
5. **DNS: proxied (orange-cloud) A record for `cirofeliu.es`.** Unlike the server's existing DNS-only subdomains (which are direct SSH/game-server connections that must bypass Cloudflare's HTTP(S)-only proxy), this is a website — proxying it gets Cloudflare's CDN/WAF for free and matches the point of buying the domain for a public portfolio. The existing DDNS script only manages Ciro's other domain's subdomains under one zone ID; `cirofeliu.es` is a separate zone, so it needs its own DDNS script instance (same pattern, different `CF_ZONE_ID`/domain, `proxied:true`), not an addition to the existing subdomain list.
7. **Origin firewall: UFW on 80/443 allows only Cloudflare's published IP ranges, not `any`.** Because the server's other DNS-only subdomains are direct (gray-cloud) A records on this same server, its real public IP is trivially discoverable — proxying `cirofeliu.es` alone doesn't hide the origin. Without this, anyone with the IP can bypass Cloudflare's proxy (WAF, rate limiting, DDoS mitigation) and hit the origin directly. Restricting the port to Cloudflare's IP list closes that gap; the alternative (Cloudflare Authenticated Origin Pulls / mTLS) is stronger but adds cert-pinning complexity not justified for v1.
6. **CI trigger: GitHub Actions `on: push: branches: [master]`**, running `./gradlew :webApp:wasmJsBrowserDistribution` then `rsync`-ing the resulting `productionExecutable` output to the server. `master` is already defined as the release branch in `AGENTS.md`, so a push there (i.e. a merged PR) is already the "ship it" signal — no extra tagging step needed.

## Risks / Trade-offs

- [Overwrite-on-deploy means a broken build can go live with no rollback path] → `./gradlew test` runs as a required step before `rsync`; a failing build/test never reaches the transfer step. Recovery from a bad-but-passing deploy is `git revert` + re-push, not an infra-level rollback — acceptable for v1's traffic/risk profile.
- [New deploy SSH key is a new secret surface] → Scope it as tightly as the read-only key (forced command, restricted to one path, no interactive shell), store only as a GitHub Actions encrypted secret, document that it must never be reused for anything else.
- [This is the server's first Cloudflare-proxied domain and first Origin Certificate — no prior art here to lean on] → Validate manually end-to-end (curl from outside the network, TLS handshake, security headers, SSL mode) before pointing the CI workflow at it; treat group 1-2 of `tasks.md` as genuinely exploratory, not a known-good recipe being repeated.
- [The existing reverse-proxy example's Caddy also wants ports 80/443 — if that stack is ever started while nexus's is running, they'll conflict on the host] → Not a problem today (confirmed it's not currently running, ports 80/443 are free), but flagged here so it isn't a surprise later; resolving it (e.g. a single shared Caddy instance with multiple site blocks) is out of scope for this change.
- [Server's real origin IP is already public via other DNS-only records, undermining the point of proxying `cirofeliu.es`] → UFW on 80/443 allow-lists only Cloudflare's published IP ranges (see Decision 7); direct-IP requests are dropped at the firewall regardless of what the attacker knows.
- [`ufw` change and Cloudflare zone setup are manual, one-time steps outside CI] → Listed explicitly as prerequisite tasks so they're never silently assumed done; the workflow fails loudly (SSH/DNS unreachable) if skipped, rather than silently no-op-ing.

## Migration Plan

1. One-time manual: Cloudflare zone/DNS record + Origin Certificate for `cirofeliu.es`; `ufw allow 80,443`.
2. One-time manual: create the new Compose + Caddyfile directory on the server, `docker compose up -d`.
3. One-time manual: generate the deploy-only SSH key pair, install the public half server-side with the forced `rsync` command, add the private half as a GitHub Actions secret.
4. Land `.github/workflows/deploy.yml` in this repo (this change).
5. First deploy: merge this change to `master`, watch the Action run, verify `https://cirofeliu.es` serves the current build.

Rollback: revert the merge commit on `master` (re-triggers the workflow with the prior build) — no separate infra-level rollback mechanism in v1.

## Open Questions

- ~~`www.cirofeliu.es` handling~~ — resolved: `www.cirofeliu.es` gets its own proxied A record and a Caddy site block that 301-redirects to `https://cirofeliu.es{uri}`; it never serves content directly.
- Exact deploy target path server-side (e.g. `/srv/nexus/dist` vs. a path mounted directly into the Caddy container) — left for implementation, following the `transfer/` precedent.
