package app.luxion.nexus.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class PortfolioSectionTest {

    @Test
    fun entriesAreDeclaredInDisplayOrder() {
        assertEquals(
            listOf(
                PortfolioSection.HeroAbout,
                PortfolioSection.Skills,
                PortfolioSection.Experience,
                PortfolioSection.Projects,
                PortfolioSection.Contact,
            ),
            PortfolioSection.entries.toList(),
        )
    }
}
