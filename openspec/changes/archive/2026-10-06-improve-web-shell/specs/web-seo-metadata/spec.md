## MODIFIED Requirements

### Requirement: Web app declares Open Graph tags
The system SHALL serve `index.html` with `og:title`, `og:description`, `og:type`, `og:url`, `og:locale` and an `og:image` that is a raster image (PNG or JPEG, 1200x630) referenced by an absolute URL, plus `twitter:card` set to `summary_large_image`, so link previews render correctly on platforms that do not support SVG or relative image URLs.

#### Scenario: Sharing the portfolio link
- **WHEN** the portfolio URL is shared on a platform that renders Open Graph previews (e.g. LinkedIn, Slack, WhatsApp, X)
- **THEN** the preview card shows the portfolio's title, description, and image instead of a blank or generic card

## ADDED Requirements

### Requirement: Web app declares structured data
The system SHALL serve `index.html` with JSON-LD describing Ciro as a `Person` (name, job title, site URL, image and profile links).

#### Scenario: Search engine reads the page
- **WHEN** a crawler parses `index.html` without executing the Wasm bundle
- **THEN** it finds a valid `Person` structured-data object with name, job title and profile links

### Requirement: Web app provides a no-JavaScript fallback
The system SHALL include a `<noscript>` block with Ciro's name, role, a short summary and contact links.

#### Scenario: JavaScript is disabled
- **WHEN** the page is loaded without JavaScript
- **THEN** the visitor sees the name, role, summary and contact links instead of an empty page

### Requirement: Document language follows the active language
The web app SHALL keep the document's `lang` attribute and title in sync with the portfolio's active language.

#### Scenario: Visitor switches to Spanish
- **WHEN** the active language changes to Spanish
- **THEN** `document.documentElement.lang` becomes `es` and the document title shows the Spanish title

### Requirement: Loading screen matches the site theme
The HTML loading screen SHALL use the same background and foreground colors as the portfolio for both light and dark system preferences, and SHALL stay visible until the app's fonts are ready and its first frame is composed.

#### Scenario: Dark-mode visitor loads the site
- **WHEN** a visitor with a dark system preference loads the portfolio
- **THEN** the loading screen and the first app frame share the same dark background, with no white flash in between
