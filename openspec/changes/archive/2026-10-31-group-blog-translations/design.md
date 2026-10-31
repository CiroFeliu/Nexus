## Context

`BlogCatalog` holds the published posts (or every post when previewing drafts with `-Pblog.drafts=true`) sorted newest first, and already pairs translations through `translationOf` (same `translationKey`, other language; a key may be used at most once per language). `BlogPages.listPage()` and `blogFeed()` both iterate `catalog.posts` directly, which is why a translated article appears twice. See proposal.md for the motivation.

## Goals / Non-Goals

**Goals:**
- One list entry and one feed item per article, built from a single grouping shared by the list and the feed.

**Non-Goals:**
- Changing post pages, their URLs, `hreflang` alternates or translation links.
- Supporting more than the two existing languages or choosing the shown version by the visitor's language.

## Decisions

- **Grouping lives in `BlogCatalog`.** A new `entries` list of `BlogEntry(primary, versions, date)` is derived from the already-filtered `posts`, so drafts are excluded (or included when previewing) exactly as today, and the list page and the feed cannot disagree. Alternative: grouping separately in `BlogPages` and `blogFeed` — duplicated logic that could drift.
- **Primary version**: the English post when the group has one, otherwise its only post. With the existing rule of one post per key and language, a group holds at most one English and one Spanish post.
- **Entry date and order**: `date` is the latest `date` among the versions; entries are sorted by that date, newest first, ties broken by the primary's slug (the existing tie-breaker applied to the entry).
- **Version links**: rendered in the entry's meta line where the language badge was, ordered `ES · EN` as requested, each a link to the version's page with `hreflang`/`lang` and the language name as title. An untranslated post shows a single link to itself, so every entry has the same shape. Post pages keep their language badge (they don't change).
- **Feed item**: built from the primary version (title, link, guid, summary, categories) with the entry's date as `pubDate`. Keeping the English URL as guid means existing subscribers don't see the English item as new; the Spanish item of a translated article disappears.
- **Draft preview**: when drafts are included, an entry shows the draft badge if any of its versions is a draft. Preview only, never in production.

## Risks / Trade-offs

- [A Spanish-only reader no longer sees the Spanish summary in the list or feed for translated articles] → The `ES` link on the entry leads straight to the Spanish version; this matches the decision that the list is in English.
- [A translated article whose Spanish version is published later bumps the entry's date, and its feed `pubDate`] → Intended: the date reflects the most recent version, as requested.
