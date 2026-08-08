package app.luxion.nexus.i18n

import java.util.prefs.Preferences

private const val KEY_LANGUAGE = "language"
private val preferences: Preferences = Preferences.userRoot().node("app.luxion.nexus")

actual fun readPersistedLanguage(): Language? =
    preferences.get(KEY_LANGUAGE, null)?.let(Language::fromTag)

actual fun persistLanguage(language: Language) {
    preferences.put(KEY_LANGUAGE, language.tag)
}
