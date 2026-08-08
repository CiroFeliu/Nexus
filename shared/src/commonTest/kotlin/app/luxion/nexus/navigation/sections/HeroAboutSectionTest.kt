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

    // Matches any composable that renders text, regardless of its content — used to assert
    // node counts without depending on exact copy, which is expected to become i18n-keyed
    // by `add-i18n-support`.
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

        // The name isn't translated, so it's a stable anchor even once i18n lands.
        onNodeWithText("Ciro Feliu").assertExists()
        onNodeWithText("CF").assertExists()
        // Avatar initials + name + role + bio: an avatar and 3 body text nodes.
        onAllNodes(hasAnyText).assertCountEquals(4)
    }
}
