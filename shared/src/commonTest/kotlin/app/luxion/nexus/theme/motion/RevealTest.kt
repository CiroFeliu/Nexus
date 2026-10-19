package app.luxion.nexus.theme.motion

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import app.luxion.nexus.theme.LocalReducedMotion
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class RevealTest {

    @Test
    fun revealTargetsStartVisibleUnderReducedMotion() = runComposeUiTest {
        val firstFrame = mutableListOf<Float>()
        mainClock.autoAdvance = false
        setContent {
            CompositionLocalProvider(LocalReducedMotion provides true) {
                (0..5).forEach { order ->
                    val progress = rememberRevealProgress(revealed = false, order = order)
                    if (firstFrame.size <= order) firstFrame += progress.value
                }
            }
        }

        assertEquals(List(6) { 1f }, firstFrame)
    }

    @Test
    fun revealTargetsStartHiddenWithMotionAllowed() = runComposeUiTest {
        var initial = -1f
        mainClock.autoAdvance = false
        setContent {
            CompositionLocalProvider(LocalReducedMotion provides false) {
                val progress = rememberRevealProgress(revealed = false)
                if (initial < 0f) initial = progress.value
            }
        }

        assertEquals(0f, initial)
    }

    @Test
    fun staggerIsCappedAtFiveSteps() {
        assertEquals(0, staggerDelayMillis(0))
        assertEquals(staggerDelayMillis(4), staggerDelayMillis(9))
    }
}
