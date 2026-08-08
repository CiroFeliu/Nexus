package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class SkillsSectionTest {

    // Mirrors the category labels declared in `SkillsSection`'s private `skillCategories`
    // list. Their presence confirms all four category groups rendered.
    private val expectedCategoryLabels = listOf(
        "Languages",
        "Mobile / Android",
        "Architecture & Patterns",
        "Tools & Platforms",
    )

    @Test
    fun rendersTitleAndEveryCategoryGroup() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                SkillsSection.Content()
            }
        }

        onNodeWithText(SkillsSection.title).assertExists()
        expectedCategoryLabels.forEach { label ->
            onNodeWithText(label).assertExists()
        }
    }
}
