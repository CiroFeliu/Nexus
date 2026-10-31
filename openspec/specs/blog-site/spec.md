# blog-site Specification

## Purpose
Publish the portfolio's blog as static, indexable HTML pages and an RSS feed generated at build time into the web distribution, so posts get stable URLs, native media and code rendering, and ship through the existing deploy without server changes.

## Requirements

### Requirement: Blog is generated into the web distribution
The system SHALL generate the blog as static files under `blog/` inside the web app's distribution output, so the production build that the deploy publishes and the development server both serve it with no change to the deploy workflow or the server.

#### Scenario: Production build
- **WHEN** `./gradlew :webApp:wasmJsBrowserDistribution` runs
- **THEN** the distribution contains `blog/index.html`, `blog/feed.xml`, the blog stylesheet and fonts, and one `blog/<slug>/index.html` per published post with its media

#### Scenario: Output is reproducible
- **WHEN** the build runs twice on the same posts
- **THEN** the generated files are byte-identical, so the deploy only transfers files whose content changed

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

### Requirement: Empty state
The system SHALL render `/blog/` with an explicit empty-state message, a link back to the portfolio and a valid (empty) feed when there are no published posts.

#### Scenario: No published posts
- **WHEN** every post is a draft or there are no posts
- **THEN** `/blog/` says there are no posts yet and `/blog/feed.xml` is a valid RSS document with no items

### Requirement: Post page
The system SHALL serve each published post at `/blog/<slug>/` with the post's language as the document language, its title, date, language, tags, rendered body, a link back to `/blog/` and a link to the portfolio home, with interface labels in the post's language.

#### Scenario: Spanish post
- **WHEN** a post declares `lang: es`
- **THEN** its page declares `lang="es"` and its interface labels (back link, translation link) are in Spanish

### Requirement: Translation links
The system SHALL link each post page to its other-language version when a published post shares its `translationKey`, and SHALL render no translation link otherwise.

#### Scenario: Translation available
- **WHEN** published posts `shogun-intro` (en) and `shogun-intro-es` (es) share a `translationKey`
- **THEN** each page links to the other and declares it as an alternate language version

#### Scenario: Translation is a draft
- **WHEN** the only other-language version is a draft
- **THEN** the published post shows no translation link

### Requirement: Self-hosted video
The system SHALL render an image reference to a `.mp4` or `.webm` file as a figure holding a `<video>` element with controls, `preload="metadata"` and `playsinline`, sourced from the post's folder, with the reference's alt text as the figure caption.

#### Scenario: MP4 video
- **WHEN** a post contains `![Agents working in parallel](demo.mp4)` on its own line
- **THEN** the page shows a playable video of `demo.mp4` captioned "Agents working in parallel", and `demo.mp4` is published next to the page

### Requirement: YouTube video
The system SHALL render an image reference to a `youtube.com/watch?v=<id>` or `youtu.be/<id>` URL as a responsive 16:9 figure holding an iframe that loads `https://www.youtube-nocookie.com/embed/<id>`, titled and captioned with the alt text.

#### Scenario: YouTube link
- **WHEN** a post contains `![Launch demo](https://www.youtube.com/watch?v=dQw4w9WgXcQ)` on its own line
- **THEN** the page embeds `https://www.youtube-nocookie.com/embed/dQw4w9WgXcQ` with the caption "Launch demo"

#### Scenario: Unrecognised YouTube URL
- **WHEN** a YouTube reference has no extractable video id
- **THEN** the build fails with a message naming the post and the URL

### Requirement: Images and code blocks
The system SHALL render any other image reference as an image with its alt text and lazy loading, and each fenced code block as preformatted code carrying its language tag as a `language-<tag>` class.

#### Scenario: Kotlin code block
- **WHEN** a post contains a fenced block tagged `kotlin`
- **THEN** the page shows it as `<pre><code class="language-kotlin">` with its content escaped

### Requirement: Untrusted markup is neutralised
The system SHALL escape raw HTML written in a post's Markdown and SHALL NOT emit `javascript:` or other unsafe link targets, so a post cannot inject scripts into the site.

#### Scenario: Raw script tag
- **WHEN** a post's body contains `<script>alert(1)</script>`
- **THEN** the page shows it as text and contains no script element from the post

### Requirement: RSS feed
The system SHALL publish `/blog/feed.xml` as an RSS 2.0 feed with one item per list entry, in the list's order, each item built from the version the list shows, with its title, absolute link, permanent guid, summary and the entry's date as publication date, and every blog page SHALL advertise the feed.

#### Scenario: Feed item
- **WHEN** a post `shogun-intro` dated 2026-11-03 is published
- **THEN** the feed has an item linking to `https://cirofeliu.es/blog/shogun-intro/` with pubDate 3 Nov 2026 and the post's summary as description

#### Scenario: Translated article in the feed
- **WHEN** published posts `shogun-intro` (en, 2026-11-03) and `shogun-intro-es` (es, 2026-11-10) share a `translationKey`
- **THEN** the feed has a single item for them, linking to `https://cirofeliu.es/blog/shogun-intro/` with pubDate 10 Nov 2026, and no item for `shogun-intro-es`

### Requirement: Pages are indexable
The system SHALL give each blog page a title, a meta description (the post summary, or a blog description on the list), a canonical absolute URL under `https://cirofeliu.es/blog/` and Open Graph tags.

#### Scenario: Post metadata
- **WHEN** a crawler fetches `/blog/shogun-intro/`
- **THEN** the HTML itself, without running JavaScript, holds the post's title, summary, canonical URL and rendered body

### Requirement: Blog matches the portfolio's look
The system SHALL style blog pages with the portfolio's design tokens (Geist and Geist Mono fonts, the light and dark colour schemes) and follow the visitor's light/dark preference, keeping long content readable on phone widths with no horizontal page scroll.

#### Scenario: Dark preference
- **WHEN** the visitor's system prefers a dark colour scheme
- **THEN** blog pages use the portfolio's dark background, text and accent colours

#### Scenario: Wide code block on a phone
- **WHEN** a code block is wider than a phone screen
- **THEN** the code block scrolls horizontally on its own and the page does not
