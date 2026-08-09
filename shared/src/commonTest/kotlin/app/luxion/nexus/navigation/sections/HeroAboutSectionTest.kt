package app.luxion.nexus.navigation.sections

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HeroAboutSectionTest {

    private val hasAnyText = SemanticsMatcher("has text") { node ->
        node.config.contains(SemanticsProperties.Text)
    }

    private class FakePainter : Painter() {
        override val intrinsicSize: Size = Size.Unspecified
        override fun DrawScope.onDraw() = Unit
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

    @Test
    fun rendersInitialsWhenNoPhotoSupplied() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                HeroAboutSection.Content(photo = null)
            }
        }

        onNodeWithText("CF").assertExists()
        onNodeWithContentDescription("Ciro Feliu").assertDoesNotExist()
    }

    @Test
    fun rendersPhotoInsteadOfInitialsWhenSupplied() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                HeroAboutSection.Content(photo = FakePainter())
            }
        }

        onNodeWithText("CF").assertDoesNotExist()
        onNodeWithContentDescription("Ciro Feliu").assertExists()
    }
}
