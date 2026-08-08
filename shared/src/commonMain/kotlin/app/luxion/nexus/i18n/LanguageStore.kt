package app.luxion.nexus.i18n

// Reads a manually-selected language previously persisted on this device, or null if the
// visitor has never overridden the system-locale default.
expect fun readPersistedLanguage(): Language?

// Persists a manually-selected language so it survives an app restart/new session.
expect fun persistLanguage(language: Language)
