package app.luxion.nexus.navigation.sections

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class HeroAboutSectionTest {

    private class FakePainter : Painter() {
        override val intrinsicSize: Size = Size.Unspecified
        override fun DrawScope.onDraw() = Unit
    }

    @Test
    fun rendersNameRoleIntroAndBothActions() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                HeroAboutSection.Content()
            }
        }

        val english = HeroAboutSection.content.getValue(Language.English)
        onNodeWithText(english.name).assertExists()
        onNodeWithText(english.role).assertExists()
        onNodeWithText(english.intro).assertExists()
        onNodeWithText("Download CV").assertExists()
        onNodeWithText("Contact").assertExists()
    }

    @Test
    fun rendersActionsInSpanish() = runComposeUiTest {
        setContent {
            PortfolioTheme {
                CompositionLocalProvider(LocalAppLanguage provides Language.Spanish) {
                    HeroAboutSection.Content()
                }
            }
        }

        onNodeWithText("Descargar CV").assertExists()
        onNodeWithText("Contacto").assertExists()
    }

    @Test
    fun contactActionInvokesCallback() = runComposeUiTest {
        var contactClicks = 0
        setContent {
            PortfolioTheme {
                HeroAboutSection.Content(onContactClick = { contactClicks++ })
            }
        }

        onNodeWithText("Contact").performClick()

        assertEquals(1, contactClicks)
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

    @Test
    fun introStaysWithinTwentyWordsInEveryLanguage() {
        Language.entries.forEach { language ->
            val words = HeroAboutSection.content.getValue(language).intro.split(' ').filter { it.isNotBlank() }
            assertTrue(words.size <= 20, "${language.name} intro has ${words.size} words")
        }
    }
}
