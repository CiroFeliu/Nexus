package app.luxion.nexus.navigation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PortfolioShellTest {

    // One stable, section-unique piece of text per declared section, used to confirm that
    // section's content actually rendered as part of the shell. Every section renders its
    // own `title` as visible text except HeroAboutSection, which doesn't — so its anchor is
    // the one other stable string its content is guaranteed to show.
    private val sectionAnchors = mapOf(
        PortfolioSection.HeroAbout to "Ciro Feliu",
        PortfolioSection.Skills to PortfolioSection.Skills.title,
        PortfolioSection.Experience to PortfolioSection.Experience.title,
        PortfolioSection.Projects to PortfolioSection.Projects.title,
        PortfolioSection.Contact to PortfolioSection.Contact.title,
    )

    @Test
    fun rendersEveryDeclaredSection() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                PortfolioShell()
            }
        }

        PortfolioSection.entries.forEach { section ->
            val anchor = sectionAnchors.getValue(section)
            onNodeWithText(anchor).assertExists()
        }
    }
}
