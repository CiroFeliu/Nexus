package app.luxion.nexus.i18n

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DateRangeTest {

    @Test
    fun formatsClosedRangeInEnglish() {
        assertEquals("Dec 2021 - Feb 2025", formatDateRange(YearMonth(2021, 12), YearMonth(2025, 2), Language.English))
    }

    @Test
    fun formatsClosedRangeInSpanish() {
        assertEquals("dic 2021 - feb 2025", formatDateRange(YearMonth(2021, 12), YearMonth(2025, 2), Language.Spanish))
    }

    @Test
    fun formatsOngoingRangePerLanguage() {
        assertEquals("Aug 2024 - Present", formatDateRange(YearMonth(2024, 8), null, Language.English))
        assertEquals("ago 2024 - actualidad", formatDateRange(YearMonth(2024, 8), null, Language.Spanish))
    }

    @Test
    fun rejectsInvalidMonth() {
        assertFailsWith<IllegalArgumentException> { YearMonth(2024, 13) }
    }
}
