package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ExperienceSectionTest {

    // Mirrors the companies declared in `ExperienceSection`'s private `entries` list. Company
    // names aren't translated, unlike role titles and descriptions, so they're stable anchors
    // for each timeline entry.
    private val expectedCompanies = listOf("FERMAX", "Freelance", "S2 Grupo", "rudo apps")

    @Test
    fun rendersTitleAndEveryTimelineEntry() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ExperienceSection.Content()
            }
        }

        onNodeWithText(ExperienceSection.title).assertExists()
        expectedCompanies.forEach { company ->
            onNodeWithText(company).assertExists()
        }
    }
}
