package app.luxion.nexus.i18n

import platform.Foundation.NSUserDefaults

private const val KEY_LANGUAGE = "language"

actual fun readPersistedLanguage(): Language? =
    NSUserDefaults.standardUserDefaults.stringForKey(KEY_LANGUAGE)?.let(Language::fromTag)

actual fun persistLanguage(language: Language) {
    NSUserDefaults.standardUserDefaults.setObject(language.tag, forKey = KEY_LANGUAGE)
}
