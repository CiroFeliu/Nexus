package app.luxion.nexus.i18n

// Returns the platform's system/browser locale as a language tag (e.g. "en-US", "es"). See the
// `actual` implementation for each target.
expect fun systemLanguageTag(): String
