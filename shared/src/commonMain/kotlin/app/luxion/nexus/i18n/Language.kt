package app.luxion.nexus.i18n

import androidx.compose.runtime.staticCompositionLocalOf

// The two content languages this portfolio ships with. Adding a language later means adding
// an entry here and a translation to each section's `Map<Language, XContent>` — no other
// structural change required.
enum class Language(val tag: String) {
    English("en"),
    Spanish("es");

    companion object {
        val Default = English

        // Resolves a BCP-47-ish language tag (e.g. "es-ES", "en-US") to a supported [Language],
        // matching only the leading language subtag. Falls back to [Default] for anything else.
        fun fromTag(tag: String): Language =
            entries.firstOrNull { tag.startsWith(it.tag, ignoreCase = true) } ?: Default
    }
}

// App-wide active language, provided once from `App()` — mirrors how `MaterialTheme` provides
// theme values app-wide. Every section reads `LocalAppLanguage.current` to pick its content.
val LocalAppLanguage = staticCompositionLocalOf { Language.Default }
