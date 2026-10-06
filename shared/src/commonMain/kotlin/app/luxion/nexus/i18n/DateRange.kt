package app.luxion.nexus.i18n

data class YearMonth(val year: Int, val month: Int) {
    init {
        require(month in 1..12) { "Month must be between 1 and 12, was $month" }
    }
}

private val monthAbbreviations = mapOf(
    Language.English to listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"),
    Language.Spanish to listOf("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sept", "oct", "nov", "dic"),
)

private val ongoingLabel = mapOf(
    Language.English to "Present",
    Language.Spanish to "actualidad",
)

fun YearMonth.format(language: Language): String =
    "${monthAbbreviations.getValue(language)[month - 1]} $year"

fun formatDateRange(start: YearMonth, end: YearMonth?, language: Language): String =
    "${start.format(language)} - ${end?.format(language) ?: ongoingLabel.getValue(language)}"
