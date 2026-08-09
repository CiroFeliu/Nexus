package app.luxion.nexus.navigation.sections

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HeroAboutSectionTest {

    private val hasAnyText = SemanticsMatcher("has text") { node ->
        node.config.contains(SemanticsProperties.Text)
    }

    @Test
    fun rendersNameAvatarAndBodyText() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                HeroAboutSection.Content()
            }
        }

        onNodeWithText("Ciro Feliu").assertExists()
        onNodeWithText("CF").assertExists()
        onAllNodes(hasAnyText).assertCountEquals(4)
    }
}
