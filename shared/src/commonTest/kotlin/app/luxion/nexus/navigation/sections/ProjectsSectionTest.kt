package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ProjectsSectionTest {

    private val titleEnglish = "Projects"

    private val expectedProjectNames = ProjectsSection.projects.map { it.content.getValue(Language.English).name }

    @Test
    fun rendersTitleAndEveryProjectCard() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ProjectsSection.Content()
            }
        }

        onNodeWithText(titleEnglish).assertExists()
        expectedProjectNames.forEach { name ->
            onNodeWithText(name).assertExists()
        }
    }
}
