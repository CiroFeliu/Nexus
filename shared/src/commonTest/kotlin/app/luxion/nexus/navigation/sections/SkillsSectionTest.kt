package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import app.luxion.nexus.theme.WidthClass
import kotlin.test.Test
import kotlin.test.assertEquals

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
        onNodeWithText("Hexagonal Architecture").assertExists()
    }

    @Test
    fun columnCountFollowsWidthClass() {
        assertEquals(1, skillColumnCount(WidthClass.Compact))
        assertEquals(2, skillColumnCount(WidthClass.Medium))
        assertEquals(4, skillColumnCount(WidthClass.Expanded))
        assertEquals(4, skillColumnCount(WidthClass.Large))
    }
}
