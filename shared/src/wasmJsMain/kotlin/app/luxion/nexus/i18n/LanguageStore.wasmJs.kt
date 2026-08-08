package app.luxion.nexus.i18n

private const val KEY_LANGUAGE = "nexus-language"

actual fun readPersistedLanguage(): Language? =
    readLocalStorageItem(KEY_LANGUAGE)?.let(Language::fromTag)

actual fun persistLanguage(language: Language) {
    writeLocalStorageItem(KEY_LANGUAGE, language.tag)
}

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
private fun readLocalStorageItem(key: String): String? =
    js("localStorage.getItem(key)")

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
private fun writeLocalStorageItem(key: String, value: String) {
    js("localStorage.setItem(key, value)")
}
