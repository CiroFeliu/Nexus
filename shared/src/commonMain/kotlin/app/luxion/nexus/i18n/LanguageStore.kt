package app.luxion.nexus.i18n

expect fun readPersistedLanguage(): Language?

expect fun persistLanguage(language: Language)
