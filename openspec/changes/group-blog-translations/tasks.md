# Tasks

## 1. Grouping

- [ ] 1.1 Add `BlogEntry` and `BlogCatalog.entries` (group published posts by `translationKey`, English primary or the only version, entry date = latest version date, newest first with the primary's slug as tie-breaker); verify with `BlogCatalogTest` cases for a translated pair, a draft translation, untranslated posts and the grouped entry date

## 2. List page and feed

- [ ] 2.1 Render the list from `catalog.entries`: primary's title, summary and tags, entry date, `ES · EN` version links instead of the language badge, draft badge when previewing; style the version links in `blog.css`; verify with `BlogPagesTest` cases for every "Blog list page" scenario and that post pages keep their badge and translation link
- [ ] 2.2 Build `feed.xml` items from `catalog.entries` (primary's title/link/guid/summary/tags, entry date as `pubDate`); verify with `BlogFeedTest` cases for "Feed item" and "Translated article in the feed"
- [ ] 2.3 Update `BlogGeneratorTest` for the fixture pair (one list entry, one feed item) and run `./gradlew :blogGenerator:test` and `./gradlew :webApp:wasmJsBrowserDistribution`, then check the generated `blog/index.html` and `blog/feed.xml` show the real ShogunAi article once

## Workflow follow-up

- Archive the change in the same branch before opening the pull request to `develop`.
