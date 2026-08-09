package app.luxion.nexus.i18n

import androidx.compose.runtime.staticCompositionLocalOf

enum class Language(val tag: String) {
    English("en"),
    Spanish("es");

    companion object {
        val Default = English

        fun fromTag(tag: String): Language =
            entries.firstOrNull { tag.startsWith(it.tag, ignoreCase = true) } ?: Default
    }
}

val LocalAppLanguage = staticCompositionLocalOf { Language.Default }
