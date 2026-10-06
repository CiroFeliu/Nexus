package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class SkillsSectionTest {

    private val titleEnglish = "Skills & stack"

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
