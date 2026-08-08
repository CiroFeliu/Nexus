package app.luxion.nexus.i18n

import kotlinx.browser.localStorage

private const val KEY_LANGUAGE = "nexus-language"

actual fun readPersistedLanguage(): Language? =
    localStorage.getItem(KEY_LANGUAGE)?.let(Language::fromTag)

actual fun persistLanguage(language: Language) {
    localStorage.setItem(KEY_LANGUAGE, language.tag)
}
