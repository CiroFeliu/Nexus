## ADDED Requirements

### Requirement: Web app declares a real title and description
The system SHALL serve `index.html` with a `<title>` naming Ciro Feliu and his role, and a `<meta name="description">` summarizing the portfolio, instead of the generic placeholder title.

#### Scenario: Browser tab and search result
- **WHEN** the web app is loaded or indexed by a search engine
- **THEN** the browser tab and any search result show the real name/role title, and the description meta tag summarizes the portfolio

### Requirement: Web app declares Open Graph tags
The system SHALL serve `index.html` with `og:title`, `og:description`, `og:type`, and `og:image` meta tags so link previews render correctly.

#### Scenario: Sharing the portfolio link
- **WHEN** the portfolio URL is shared on a platform that renders Open Graph previews (e.g. LinkedIn, Slack)
- **THEN** the preview card shows the portfolio's title, description, and image instead of a blank or generic card

### Requirement: Web app declares a favicon
The system SHALL serve a favicon referenced from `index.html` so the browser tab shows a project-specific icon instead of the browser default.

#### Scenario: Viewing the app in a browser tab
- **WHEN** the web app is open in a browser tab
- **THEN** the tab shows the portfolio's favicon instead of a blank/default icon
