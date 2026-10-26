# Proposal

## Why

The first deploy of the blog (PR #38, 2026-10-26) left `https://cirofeliu.es` broken: `webApp.js` and `styles.css` have no content hash in their names, the origin sends no `Cache-Control` for them, so Cloudflare applied its default caching and kept serving the `webApp.js` from 2026-10-19 (`cf-cache-status: HIT`, `cache-control: max-age=14400`). That stale script requested two `.wasm` files the deploy's `rsync --delete` had just removed, both returned 404 and the home page never loaded, while `/` and `/blog/` (not cached by Cloudflare) were fine. A manual Cloudflare purge fixed it. Nothing in the deploy prevents it from happening again on the next release that changes the Wasm binaries.

## What Changes

- The origin serves every file whose name is not content-hashed (`index.html`, `webApp.js`, `styles.css`, `favicon.svg`, `og-image.png`, Compose resources, the blog's HTML, feed, stylesheet, fonts and media) with `Cache-Control: no-cache`, so browsers and Cloudflare revalidate them on every use instead of trusting a copy for hours.
- The origin serves the content-hashed `.wasm` binaries with `Cache-Control: public, max-age=31536000, immutable`.
- The Cloudflare zone stops overriding origin cache headers (Browser Cache TTL set to respect existing headers).
- The deploy workflow publishes atomically: new files are put in place together and obsolete ones are deleted only after every new file is in place, so a request during the transfer never sees a `webApp.js` whose `.wasm` files are missing.
- After publishing, the deploy workflow purges the whole Cloudflare cache of the `cirofeliu.es` zone with an API token scoped to cache purge on that zone only, and fails if the purge fails.
- After purging, the deploy workflow checks that the live `webApp.js` and every `.wasm` it references are served successfully, and fails the run otherwise, so a broken release is reported instead of discovered by visitors.
- Server configuration and Cloudflare settings are described here only at a high level; applying them, and creating and storing the purge token, are manual tasks for Ciro, because this repository is public.

## Capabilities

### New Capabilities

None.

### Modified Capabilities

- `webapp-deployment`: adds requirements for the HTTP caching policy of the published files, atomic publication, Cloudflare cache purge after each deploy, and a post-deploy check that the live entry script and its Wasm binaries load.

## Impact

- `.github/workflows/deploy.yml`: `rsync` options for atomic publication, a new purge step and a new post-deploy check step.
- New repository secrets (created by Ciro): a Cloudflare API token limited to cache purge on the `cirofeliu.es` zone, and the zone's identifier.
- Server-side Caddy configuration for `cirofeliu.es` (applied by Ciro, not stored in this repo): per-path `Cache-Control` headers.
- Cloudflare zone settings (applied by Ciro): Browser Cache TTL.
- No change to the Kotlin code, the web build or the blog generator.
