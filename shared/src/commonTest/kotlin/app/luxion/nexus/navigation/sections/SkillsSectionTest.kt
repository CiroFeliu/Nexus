package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class SkillsSectionTest {

    // `title` is private and language-keyed now, so this hardcodes the known default-language
    // (English) copy rather than reading it back.
    private val titleEnglish = "Skills & Stack"

    // Read from the package-internal `skillCategories` list (visible to tests via the friend
    // module relationship) instead of duplicating the labels, so this stays correct if
    // categories are added, removed, or reworded.
    private val expectedCategoryLabels = skillCategories.map { it.label.getValue(Language.English) }

    @Test
    fun rendersTitleAndEveryCategoryGroup() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                SkillsSection.Content()
            }
        }

        onNodeWithText(titleEnglish).assertExists()
        expectedCategoryLabels.forEach { label ->
            onNodeWithText(label).assertExists()
        }
    }
}
