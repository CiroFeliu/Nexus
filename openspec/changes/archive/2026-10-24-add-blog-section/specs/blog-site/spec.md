# Spec Delta

## Purpose

Publish the portfolio's blog as static, indexable HTML pages and an RSS feed generated at build time into the web distribution, so posts get stable URLs, native media and code rendering, and ship through the existing deploy without server changes.

## ADDED Requirements

### Requirement: Blog is generated into the web distribution
The system SHALL generate the blog as static files under `blog/` inside the web app's distribution output, so the production build that the deploy publishes and the development server both serve it with no change to the deploy workflow or the server.

#### Scenario: Production build
- **WHEN** `./gradlew :webApp:wasmJsBrowserDistribution` runs
- **THEN** the distribution contains `blog/index.html`, `blog/feed.xml`, the blog stylesheet and fonts, and one `blog/<slug>/index.html` per published post with its media

#### Scenario: Output is reproducible
- **WHEN** the build runs twice on the same posts
- **THEN** the generated files are byte-identical, so the deploy only transfers files whose content changed

### Requirement: Blog list page
The system SHALL serve `/blog/` as one list of every published post in both languages, newest `date` first (ties broken by slug), each entry showing its title linking to `/blog/<slug>/`, its date, its language and its summary, plus its tags when present.

#### Scenario: Mixed languages
- **WHEN** one English and one Spanish post are published
- **THEN** both appear in the same list, each marked with its language (`EN` or `ES`)

#### Scenario: Ordering
- **WHEN** posts dated 2026-11-03 and 2026-12-01 are published
- **THEN** the 2026-12-01 post is listed first

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
The system SHALL publish `/blog/feed.xml` as an RSS 2.0 feed of every published post, newest first, each item with its title, absolute link, permanent guid, publication date and summary, and every blog page SHALL advertise the feed.

#### Scenario: Feed item
- **WHEN** a post `shogun-intro` dated 2026-11-03 is published
- **THEN** the feed has an item linking to `https://cirofeliu.es/blog/shogun-intro/` with pubDate 3 Nov 2026 and the post's summary as description

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
