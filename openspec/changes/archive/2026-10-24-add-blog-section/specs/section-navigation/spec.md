# Spec Delta

## ADDED Requirements

### Requirement: Navigation links to the blog
The navigation bar on non-compact layouts and the compact full-screen menu SHALL each show a "Blog" entry after the section links, visually set apart from them and never highlighted as the active section, that opens the blog: `/blog/` of the current site on the web, `https://cirofeliu.es/blog/` on Android, iOS and desktop.

#### Scenario: Visitor opens the blog from the bar
- **WHEN** a visitor on a non-compact layout activates the Blog entry
- **THEN** the blog list opens through the shared URL-opening capability and the portfolio's scroll position is left unchanged

#### Scenario: Visitor opens the blog from the compact menu
- **WHEN** a visitor on a compact layout opens the menu and activates the Blog entry
- **THEN** the menu closes and the blog list opens

#### Scenario: Native app
- **WHEN** a visitor activates the Blog entry on Android, iOS or desktop
- **THEN** `https://cirofeliu.es/blog/` opens in the platform's browser
