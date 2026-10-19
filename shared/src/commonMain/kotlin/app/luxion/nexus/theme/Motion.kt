package app.luxion.nexus.theme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

object PortfolioMotion {
    const val SHORT_MILLIS = 150
    const val MEDIUM_MILLIS = 250
    const val ENTRY_MILLIS = 600
    const val STAGGER_MILLIS = 60
    val emphasizedEasing = CubicBezierEasing(0.16f, 1f, 0.3f, 1f)

    fun <T> feedback(): AnimationSpec<T> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow,
    )

    fun <T> transition(): AnimationSpec<T> = tween(MEDIUM_MILLIS, easing = emphasizedEasing)

    fun <T> entry(delayMillis: Int = 0): AnimationSpec<T> =
        tween(ENTRY_MILLIS, delayMillis = delayMillis, easing = emphasizedEasing)
}

val LocalReducedMotion = staticCompositionLocalOf { false }

@Composable
@ReadOnlyComposable
fun <T> motionSpec(spec: AnimationSpec<T>): AnimationSpec<T> =
    if (LocalReducedMotion.current) snap() else spec

@Composable
expect fun rememberReducedMotionPreference(): Boolean
