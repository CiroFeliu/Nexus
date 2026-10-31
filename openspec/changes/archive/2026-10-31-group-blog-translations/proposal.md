## Why

`/blog/` lists every published post as its own entry, so an article published in English and Spanish shows up twice, and the RSS feed repeats it too. Now that the first article ships in both languages, readers see the same article twice in a row.

## What Changes

- The blog list shows one entry per article: published posts that share a `translationKey` become a single entry. Posts without a `translationKey` stay a single entry in their own language.
- A grouped entry shows the English version (the list page and the portfolio default to English); when only one version is published, it shows that one.
- The entry's language badge is replaced by links to each published version (`ES · EN`). Drafts never appear in production.
- Entries stay newest first; a grouped entry's date is the date of its most recent version.
- The RSS feed is grouped the same way: one item per article, no duplicates.
- Post pages, their URLs and their translation links don't change.

## Capabilities

### New Capabilities

None.

### Modified Capabilities

- `blog-site`: the "Blog list page" and "RSS feed" requirements change from one entry/item per post to one per article, with version links instead of a language badge on the list.

## Impact

- `:blogGenerator`: `BlogCatalog` (groups posts into entries), `BlogPages` (list entries and version links), `BlogFeed` (one item per entry), `blog.css` (version links), and their tests.
- Generated output: `/blog/index.html` and `/blog/feed.xml`. The feed loses the items of the non-English versions of translated articles; English and untranslated posts keep their item, link and guid.
- No change to post pages, URLs, the deploy or the server.
