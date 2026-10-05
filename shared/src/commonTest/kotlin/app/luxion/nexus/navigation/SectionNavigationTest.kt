package app.luxion.nexus.navigation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalTestApi::class)
class SectionNavigationTest {

    @Test
    fun rendersOneLinkPerSection() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                SectionNavigation(activeSection = null, onSectionSelected = {})
            }
        }

        onAllNodes(hasClickAction()).assertCountEquals(PortfolioSection.entries.size)
        PortfolioSection.entries.forEach { section ->
            onNodeWithText(section.label.getValue(Language.English)).assertExists()
        }
    }

    @Test
    fun clickingALinkInvokesCallbackWithThatSection() = runComposeUiTest {
        var selected: PortfolioSection? = null
        setContent {
            PortfolioTheme {
                SectionNavigation(activeSection = null, onSectionSelected = { selected = it })
            }
        }

        onNodeWithText(PortfolioSection.Projects.label.getValue(Language.English)).performClick()

        assertEquals(PortfolioSection.Projects, selected)
    }
}

class DeriveActiveSectionTest {

    private val offsets = mapOf(
        PortfolioSection.HeroAbout to 0,
        PortfolioSection.Skills to 400,
        PortfolioSection.Experience to 900,
        PortfolioSection.Projects to 1500,
        PortfolioSection.Contact to 2200,
    )

    @Test
    fun returnsFirstSectionAtTopOfPage() {
        assertEquals(PortfolioSection.HeroAbout, deriveActiveSection(0, offsets))
    }

    @Test
    fun returnsClosestSectionAtOrBeforeScrollPosition() {
        assertEquals(PortfolioSection.Skills, deriveActiveSection(650, offsets))
    }

    @Test
    fun returnsLastSectionWhenScrolledPastIt() {
        assertEquals(PortfolioSection.Contact, deriveActiveSection(5000, offsets))
    }

    @Test
    fun returnsNullWhenNoOffsetsRecordedYet() {
        assertNull(deriveActiveSection(0, emptyMap()))
    }
}
