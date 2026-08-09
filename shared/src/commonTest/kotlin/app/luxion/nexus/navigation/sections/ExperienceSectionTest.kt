package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ExperienceSectionTest {

    // `title` is private and language-keyed now, so this hardcodes the known default-language
    // (English) copy rather than reading it back.
    private val titleEnglish = "Experience"

    // Read from the package-internal `entries` list (visible to tests via the friend module
    // relationship) instead of duplicating the company names, so this stays correct as roles
    // change. Company names aren't translated, unlike role titles and descriptions, so they're
    // stable anchors for each timeline entry regardless of language.
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
