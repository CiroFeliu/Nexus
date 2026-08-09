package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ProjectsSectionTest {

    // `title` is private and language-keyed now, so this hardcodes the known default-language
    // (English) copy rather than reading it back.
    private val titleEnglish = "Projects"

    // Read from the package-internal `projects` list (visible to tests via the friend module
    // relationship) instead of duplicating every name, so this stays correct as projects are
    // added or removed. Product/project names aren't translated, unlike descriptions, so
    // they're stable anchors for each card regardless of language — and their count confirms
    // every card rendered.
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
