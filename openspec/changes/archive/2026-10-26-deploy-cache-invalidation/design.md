# Design

## Context

See proposal.md - Why for the incident. Current state, checked read-only on 2026-10-26:

- The `wasmJs` production distribution has two kinds of files. The Wasm binaries are named after their content (`<20 hex chars>.wasm`) and `webApp.js` references them by those literal names. Everything else keeps a fixed name across builds: `index.html`, `webApp.js`, `styles.css`, `favicon.svg`, `og-image.png`, `composeResources/**` (fonts, drawables) and the generated `blog/**` (HTML, `feed.xml`, `assets/blog.css`, fonts, per-post `.mp4`).
- The origin's Caddy site block for `cirofeliu.es` serves the distribution as static files and sets security headers and the Wasm `Content-Type`, but no `Cache-Control` at all.
- Cloudflare, with no origin `Cache-Control`, caches `.js`/`.css`/fonts/images at the edge by extension and adds `cache-control: max-age=14400` to the browser response (the zone's default Browser Cache TTL). HTML is not cached at the edge (`DYNAMIC`), but browsers may still cache it heuristically from `Last-Modified`.
- The deploy uploads with `rsync --delete --delete-excluded --checksum` through a write-only `rrsync` key. With rsync's default `--delete-during`, obsolete files can disappear before the new `webApp.js` arrives. The server's `rrsync` allows `--delay-updates` and `--delete-after`.
- The repository is public: the server's Caddy configuration, paths and hosts must not be committed. The deploy's existing secrets are repository-level secrets with the `NEXUS_` prefix.

## Goals / Non-Goals

**Goals:**
- A deploy can never again leave a cached entry script pointing at Wasm binaries that no longer exist, at the edge or in a returning visitor's browser.
- Keep the long-lived caching where it is safe and pays off: the Wasm binaries, which are by far the largest files.
- Make a broken release fail the CI run instead of being found by visitors.

**Non-Goals:**
- Adding content hashes to `webApp.js`, `styles.css` or the Compose resources in the build. It would allow caching them long too, but it touches webpack/Compose resource configuration for a gain that is small on a site of this size; revalidation with `304` is cheap.
- Edge caching of HTML or of the unhashed assets. Cloudflare will mostly pass these through to the origin; the origin serves small static files and can take it.
- Rollback automation or keeping several releases on the server.
- Moving the server configuration into this repository.

## Decisions

1. **Cache policy by name, not by type.** Only files whose name is a content hash may be cached long. Today that is exactly `*.wasm`, so the origin sends `Cache-Control: public, max-age=31536000, immutable` for `*.wasm` and `Cache-Control: no-cache` for every other path, blog included. Defaulting to `no-cache` means any file added later (new blog media, new resources) is safe without touching the server again; the price is a revalidation request per unhashed file per visit, answered with `304` thanks to Caddy's `ETag`/`Last-Modified`. Alternatives considered: `max-age=0, must-revalidate` (equivalent for browsers, but `no-cache` is the clearer and more widely honoured directive); a short `max-age` such as 5 minutes for the blog (still reproduces the incident inside that window; rejected); per-type rules for fonts and videos (unhashed, so they can change under the same name, and the posts' videos are small).

2. **Cloudflare respects origin headers.** The zone's Browser Cache TTL is set to "Respect Existing Headers" so Cloudflare never rewrites `no-cache` into its own `max-age`. No Cache Rules are needed: with `no-cache` from the origin Cloudflare does not serve the unhashed files from edge cache without revalidating, and it caches the `.wasm` files according to their `max-age`.

3. **Server configuration stays out of the repo.** The change states the required behaviour (the two `Cache-Control` values, which paths get which) and Ciro applies it to the server's Caddy configuration and documents it in the server's private docs. The repo's specs describe the observable headers, which is what the post-deploy check and a `curl -I` can verify.

4. **Purge everything after publishing, not a URL list.** A step after `rsync` calls Cloudflare's `purge_cache` endpoint for the zone with `{"purge_everything": true}`. The zone serves only this site (`www` just redirects), so a full purge costs at most one origin fetch per `.wasm` per edge location after each deploy. A URL list would have to be derived from the build output, kept in sync with the blog's growing set of pages, and split to respect per-request URL limits, for no practical benefit. With Decision 1 in place the purge is a second line of defence: it clears anything the edge cached before the new headers were applied, and anything cached by mistake if the server config ever regresses.
   - Credentials: a Cloudflare API token with only the `Zone > Cache Purge > Purge` permission, restricted to the `cirofeliu.es` zone, stored as the repository secret `NEXUS_CLOUDFLARE_PURGE_TOKEN`; the zone identifier as `NEXUS_CLOUDFLARE_ZONE_ID`. Both are created and stored by Ciro. The token cannot read or change any zone setting or DNS record.
   - The step uses `curl` with retries and fails unless the JSON response has `"success": true`. It runs only if publishing succeeded (default step behaviour), so a failed transfer never purges the cache of a still-live previous build.

5. **Atomic publication instead of keeping old Wasm files around.** `rsync` gains `--delay-updates` (every updated file is renamed into place at the end of the transfer) and `--delete-after` (obsolete files are removed only after that). A request during the transfer therefore sees either the complete old release or the complete new one, apart from the instant of the final renames.
   - Keeping the previous release's `.wasm` files for a time window was considered and rejected. Once `webApp.js` is `no-cache`, a browser runs a stale `webApp.js` only if it fetched it before the deploy and asks for the `.wasm` after it, which `webApp.js` does immediately on load, so the exposure is a few seconds and a reload fixes it. A window would need either server-side pruning (impossible with a write-only `rrsync` key) or CI downloading and re-uploading the previous binaries from the live site; neither is worth it for that exposure.
   - The one longer exposure is the transition: browsers that already hold a `webApp.js` with `max-age=14400` from before this change. It ends at most 4 hours after the first deploy with the new headers, and only matters if that deploy also changes the Wasm binaries. To avoid it, the server headers are applied before the workflow change is deployed (see Migration Plan).

6. **Post-deploy check against the live site.** After the purge, a step downloads `https://cirofeliu.es/webApp.js`, extracts the referenced `[0-9a-f]{20}\.wasm` names, fails if there are none, and requests each one, failing with the missing file's name on any non-`200`. It goes through Cloudflare like a visitor does, so it catches exactly the incident's failure mode (entry script and binaries out of sync at the edge). It also checks that `webApp.js` comes back with `no-cache`, so a lost server configuration is noticed on the next deploy. It does not try to render the page; that needs a browser and is out of scope.

## Risks / Trade-offs

- [More requests reach the origin, since Cloudflare no longer serves the unhashed assets from cache] → They are small static files answered mostly with `304`; the large `.wasm` files remain edge- and browser-cached for a year.
- [Purge token is a new secret] → Least privilege (cache purge on one zone only), stored only as a GitHub Actions secret, created and rotated by Ciro. A leak would let someone purge the cache, i.e. cause extra origin traffic, nothing more.
- [Cloudflare API outage fails the deploy after the files are already live] → The new release is live and correct at the origin; with Decision 1 the edge holds nothing that can go stale except `.wasm` files, which never change under a name. Re-running the workflow or purging from the dashboard is enough.
- [The post-deploy check's regex depends on how the Kotlin/Wasm webpack output names and references binaries] → The check fails loudly ("no .wasm referenced") rather than passing silently if the format changes, and the fix is a one-line regex update.
- [`--delay-updates` keeps a temporary copy of every changed file during the transfer] → The distribution is a few MB; disk use is negligible.
- [The server's Caddy configuration and the Cloudflare setting are manual and can drift from what this change describes] → The post-deploy check asserts `no-cache` on `webApp.js` on every deploy.

## Migration Plan

1. Ciro creates the Cloudflare API token (cache purge on the `cirofeliu.es` zone only) and stores it and the zone identifier as repository secrets `NEXUS_CLOUDFLARE_PURGE_TOKEN` and `NEXUS_CLOUDFLARE_ZONE_ID`.
2. Ciro applies the `Cache-Control` headers to the server's Caddy site block for `cirofeliu.es`, reloads Caddy, sets the zone's Browser Cache TTL to "Respect Existing Headers" and purges the zone once by hand. Checked with `curl -I` on `/`, `/webApp.js`, `/styles.css`, `/blog/` and one `.wasm`.
3. Merge this change to `develop`, then release to `master`; the deploy runs with atomic publication, purge and the post-deploy check.
4. Confirm in the run log that the purge reported success and the check passed.

Rollback: the workflow steps are reverted with a normal revert PR; the server headers are reverted by Ciro in the Caddy configuration. Neither depends on the other: the headers are safe without the workflow change and vice versa.
