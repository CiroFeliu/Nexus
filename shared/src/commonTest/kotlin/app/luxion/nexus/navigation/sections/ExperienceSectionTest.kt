package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ExperienceSectionTest {

    private val titleEnglish = "Experience"

    private val expectedCompanies = entries.map { it.company }

    @Test
    fun rendersTitleAndEveryTimelineEntry() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ExperienceSection.Content()
            }
        }

        onNodeWithText(titleEnglish).assertExists()
        expectedCompanies.forEach { company ->
            onNodeWithText(company).assertExists()
        }
    }
}
