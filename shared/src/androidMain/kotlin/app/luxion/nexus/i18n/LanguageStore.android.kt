package app.luxion.nexus.i18n

import android.content.Context
import app.luxion.nexus.AndroidAppContext

private const val PREFS_NAME = "nexus_prefs"
private const val KEY_LANGUAGE = "language"

private val preferences
    get() = AndroidAppContext.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

actual fun readPersistedLanguage(): Language? =
    preferences.getString(KEY_LANGUAGE, null)?.let(Language::fromTag)

actual fun persistLanguage(language: Language) {
    preferences.edit().putString(KEY_LANGUAGE, language.tag).apply()
}
