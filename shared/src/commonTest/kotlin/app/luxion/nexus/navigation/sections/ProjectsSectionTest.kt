package app.luxion.nexus.navigation.sections

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ProjectsSectionTest {

    // Mirrors the project names declared in `ProjectsSection`'s private `projects` list.
    // Product/project names aren't translated, unlike descriptions, so they're stable anchors
    // for each card — and their count confirms every card rendered.
    private val expectedProjectNames = listOf(
        "Nexus",
        "ShogunAi",
        "DuoxMe",
        "MeetMe",
        "Mhia",
        "Internal Tools",
        "Device Communications",
        "Secure Development Awareness",
        "Revieve",
        "Pinwins",
        "The Fitzgerald",
        "HCB Paciente",
        "Chatripp",
        "Hofmann",
        "Extra Promotions",
        "Zenith",
    )

    @Test
    fun rendersTitleAndEveryProjectCard() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                ProjectsSection.Content()
            }
        }

        onNodeWithText(ProjectsSection.title).assertExists()
        expectedProjectNames.forEach { name ->
            onNodeWithText(name).assertExists()
        }
    }
}
