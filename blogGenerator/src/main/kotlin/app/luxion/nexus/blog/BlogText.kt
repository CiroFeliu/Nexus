package app.luxion.nexus.blog

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class BlogLabels(
    val allPosts: String,
    val translation: String,
    val draft: String,
    val languageName: String,
)

val PostLanguage.labels: BlogLabels
    get() = when (this) {
        PostLanguage.English -> BlogLabels(
            allPosts = "All posts",
            translation = "Read in Spanish",
            draft = "Draft",
            languageName = "English",
        )
        PostLanguage.Spanish -> BlogLabels(
            allPosts = "Todas las entradas",
            translation = "Leer en inglés",
            draft = "Borrador",
            languageName = "Español",
        )
    }

val PostLanguage.ogLocale: String
    get() = when (this) {
        PostLanguage.English -> "en_US"
        PostLanguage.Spanish -> "es_ES"
    }

private val ENGLISH_MONTHS = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
private val SPANISH_MONTHS = listOf("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sept", "oct", "nov", "dic")

fun formatDisplayDate(date: LocalDate, language: PostLanguage): String {
    val months = if (language == PostLanguage.Spanish) SPANISH_MONTHS else ENGLISH_MONTHS
    return "${date.dayOfMonth} ${months[date.monthValue - 1]} ${date.year}"
}

fun formatFeedDate(date: LocalDate): String =
    DateTimeFormatter.RFC_1123_DATE_TIME.format(date.atStartOfDay(ZoneOffset.UTC))

fun escapeMarkup(text: String): String = buildString(text.length) {
    text.forEach { character ->
        when (character) {
            '&' -> append("&amp;")
            '<' -> append("&lt;")
            '>' -> append("&gt;")
            '"' -> append("&quot;")
            '\'' -> append("&#39;")
            else -> append(character)
        }
    }
}
