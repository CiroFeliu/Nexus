package app.luxion.nexus.theme.motion

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.luxion.nexus.theme.LocalReducedMotion
import app.luxion.nexus.theme.PortfolioMotion
import app.luxion.nexus.theme.motionSpec

private const val MAX_STAGGER_STEPS = 4
private val DEFAULT_RISE = 20.dp

val LocalSectionRevealed = compositionLocalOf { true }

internal fun initialRevealProgress(reducedMotion: Boolean): Float = if (reducedMotion) 1f else 0f

internal fun staggerDelayMillis(order: Int): Int =
    order.coerceIn(0, MAX_STAGGER_STEPS) * PortfolioMotion.STAGGER_MILLIS

@Composable
fun rememberRevealProgress(revealed: Boolean, order: Int = 0): State<Float> {
    val reducedMotion = LocalReducedMotion.current
    val progress = remember { Animatable(initialRevealProgress(reducedMotion)) }
    val spec = motionSpec(PortfolioMotion.entry<Float>(staggerDelayMillis(order)))
    LaunchedEffect(revealed) {
        if (revealed) progress.animateTo(1f, spec)
    }
    return progress.asState()
}

@Composable
fun Modifier.reveal(
    order: Int = 0,
    revealed: Boolean = LocalSectionRevealed.current,
    rise: Dp = DEFAULT_RISE,
): Modifier {
    val progress = rememberRevealProgress(revealed, order)
    return graphicsLayer {
        val value = progress.value
        alpha = value
        translationY = (1f - value) * rise.toPx()
    }
}
