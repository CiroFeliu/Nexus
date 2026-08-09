package app.luxion.nexus.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class PortfolioSectionTest {

    // `PortfolioSection` only carries each entry's composable content now — titles moved into
    // per-section, language-keyed maps with `add-i18n-support`, so declaration order is the
    // one property still worth asserting here; per-section content is covered by each
    // section's own test.
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
