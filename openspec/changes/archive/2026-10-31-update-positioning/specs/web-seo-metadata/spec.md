## ADDED Requirements

### Requirement: Web shell states the current headline
The system SHALL use Ciro's current public headline (title "Product engineer", statement "I build with AI agents in parallel, from spec to real device.") in `index.html`'s `<title>`, meta description, `og:title`, `og:description`, `og:image:alt`, the JSON-LD `jobTitle`, the `<noscript>` fallback and the text drawn in `og-image.png`, and SHALL NOT present him with a previous title.

#### Scenario: Link preview after the positioning update
- **WHEN** a link to `https://cirofeliu.es/` is shared and the crawler reads its metadata and preview image
- **THEN** the title, description and image show "Product engineer" and the headline statement, and none of them says "Mobile Systems Architect"

#### Scenario: Static and dynamic titles agree
- **WHEN** the web app finishes loading in English
- **THEN** the document title set by the app equals the static `<title>` in `index.html`
