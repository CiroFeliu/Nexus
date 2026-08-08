## Requirements

### Requirement: Shared URL-opening capability
The system SHALL expose a single `openUrl(url: String)` function (`expect`/`actual`) in `:shared`, implemented for every target, so any section can open an external URL in the platform's default browser/handler without redefining this per section.

#### Scenario: Section opens a URL
- **WHEN** a section calls `openUrl(url)` on any of the four targets
- **THEN** the URL opens in that platform's default browser/handler (Android `Intent`, JS/Wasm `window.open`, JVM `Desktop.browse`, iOS `UIApplication.openURL`)

#### Scenario: Two sections both need to open links
- **WHEN** more than one section (e.g., Projects and Contact) needs to open external links
- **THEN** both call the same shared `openUrl` function rather than each defining their own platform-specific implementation
