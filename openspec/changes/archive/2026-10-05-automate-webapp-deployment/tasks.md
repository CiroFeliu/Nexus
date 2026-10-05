## 1. Cloudflare & DNS setup (manual, one-time)

- [x] 1.1 Add `cirofeliu.es` as a new zone in Cloudflare (separate from Ciro's other existing zone); point the domain's nameservers at Cloudflare
- [x] 1.2 Create proxied (orange-cloud) A records for `cirofeliu.es` and `www.cirofeliu.es`, pointing at the server's current public IP
- [x] 1.3 Generate a Cloudflare Origin Certificate for `cirofeliu.es` + `www.cirofeliu.es`; set the zone's SSL/TLS mode to Full (strict)
- [x] 1.4 Create a second DDNS script/service/timer for the `cirofeliu.es` zone (own `CF_ZONE_ID`, `proxied:true`) — the existing DDNS script only covers Ciro's other domain's subdomains under a different zone ID

## 2. Server-side hosting setup (manual, one-time)

- [x] 2.1 Create a new `docker-compose.yml` and `Caddyfile` on the server, reusing the existing reverse-proxy example's security-header block
- [x] 2.2 Configure the Caddyfile: `cirofeliu.es` serves the deploy target directory as static root with the standard security headers (HSTS, X-Content-Type-Options, X-Frame-Options, Referrer-Policy, Permissions-Policy); `www.cirofeliu.es` 301-redirects to `cirofeliu.es`; both use the Cloudflare Origin Certificate (no Caddy automatic HTTPS)
- [x] 2.3 `docker compose up -d` and verify Caddy starts cleanly
- [x] 2.4 Restrict 80/443 in UFW to Cloudflare's published IP ranges only (not `allow from any`) — the server's other DNS-only records already expose its real IP, so this is the actual boundary protecting `cirofeliu.es`
- [x] 2.5 Verify `https://cirofeliu.es` and `https://www.cirofeliu.es` resolve, redirect correctly, and present a valid TLS handshake, before wiring up CI
- [x] 2.6 Verify a direct request to the server's IP on 80/443 (bypassing Cloudflare) is dropped, confirming the UFW allow-list actually works

## 3. Deploy credential

- [x] 3.1 Generate a new, dedicated deploy-only SSH key pair — never reuse the read-only exploration key or Ciro's personal key
- [x] 3.2 Install the public half in the server's `authorized_keys`, restricted via a forced `command=` running `rrsync` (write-only, locked to the deploy target directory) — not a hand-rolled `rsync --server` wrapper
- [x] 3.3 Verify the key can rsync into the deploy directory and is rejected for anything else (interactive shell, other paths, other commands)
- [x] 3.4 Add the private half as a GitHub Actions encrypted secret (e.g. `NEXUS_DEPLOY_SSH_KEY`), plus host/user/path as secrets or workflow env vars

## 4. GitHub Actions workflow

- [x] 4.1 Add `.github/workflows/deploy.yml` triggered on `push` to `master`
- [x] 4.2 Workflow step: checkout, set up JDK/Gradle
- [x] 4.3 Workflow step: `./gradlew test` (workflow stops here on failure, nothing published)
- [x] 4.4 Workflow step: `./gradlew :webApp:wasmJsBrowserDistribution`
- [x] 4.5 Workflow step: `rsync` the `productionExecutable` output to the server over SSH using the deploy key secret
- [x] 4.6 Confirm the workflow does not trigger on pushes to branches other than `master`

## 5. Validation

- [x] 5.1 Merge this change's branch to `master` and watch the workflow run end to end
- [x] 5.2 Verify `https://cirofeliu.es` serves the current build, with valid TLS and the expected security headers
- [x] 5.3 Push a trivial follow-up commit to `master` and confirm the site updates automatically with no manual step

## 6. Docs

- [x] 6.1 Confirm `AGENTS.md`'s Project section deployment note matches the final shipped setup
- [x] 6.2 Document the new deploy-only SSH key's existence and scope in `~/AGENTS.md` (private, not this repo), alongside the read-only exploration key, so future sessions know both exist and what each is for
