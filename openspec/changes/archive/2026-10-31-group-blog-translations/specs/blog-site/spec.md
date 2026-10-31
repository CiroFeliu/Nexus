## MODIFIED Requirements

### Requirement: Blog list page
The system SHALL serve `/blog/` as one list of entries, one per article: published posts sharing a `translationKey` form a single entry and every other published post is its own entry. Each entry shows the English version when published (otherwise its only version): its title linking to that version's page, its summary and its tags when present, plus the entry's date and a link to each published version labelled with its language (`ES · EN`). Entries are ordered newest date first (ties broken by slug), and an entry's date is the date of its most recent version.

#### Scenario: Mixed languages
- **WHEN** one English and one Spanish post without a `translationKey` are published
- **THEN** both appear in the same list as separate entries, each linking to its own version (`EN` or `ES`)

#### Scenario: Ordering
- **WHEN** posts dated 2026-11-03 and 2026-12-01 are published
- **THEN** the 2026-12-01 post is listed first

#### Scenario: Translated article
- **WHEN** published posts `shogun-intro` (en) and `shogun-intro-es` (es) share a `translationKey`
- **THEN** the list has a single entry for them, showing the English title and summary, linking its title to `/blog/shogun-intro/` and linking `ES` to `/blog/shogun-intro-es/` and `EN` to `/blog/shogun-intro/`

#### Scenario: Only the Spanish version is published
- **WHEN** posts share a `translationKey`, the Spanish one is published and the English one is a draft
- **THEN** the list has a single entry showing the Spanish version, with only an `ES` link and no link to the draft

#### Scenario: Grouped entry date
- **WHEN** the English version of an article is dated 2026-11-03 and its Spanish version 2026-11-10, and another post is dated 2026-11-05
- **THEN** the article's entry shows 2026-11-10 and is listed before the other post

### Requirement: RSS feed
The system SHALL publish `/blog/feed.xml` as an RSS 2.0 feed with one item per list entry, in the list's order, each item built from the version the list shows, with its title, absolute link, permanent guid, summary and the entry's date as publication date, and every blog page SHALL advertise the feed.

#### Scenario: Feed item
- **WHEN** a post `shogun-intro` dated 2026-11-03 is published
- **THEN** the feed has an item linking to `https://cirofeliu.es/blog/shogun-intro/` with pubDate 3 Nov 2026 and the post's summary as description

#### Scenario: Translated article in the feed
- **WHEN** published posts `shogun-intro` (en, 2026-11-03) and `shogun-intro-es` (es, 2026-11-10) share a `translationKey`
- **THEN** the feed has a single item for them, linking to `https://cirofeliu.es/blog/shogun-intro/` with pubDate 10 Nov 2026, and no item for `shogun-intro-es`
