package app.luxion.nexus.theme.motion

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp

fun Modifier.parallax(enabled: Boolean, scrollOffset: () -> Int, factor: Float, maxShift: Dp): Modifier =
    if (!enabled) {
        this
    } else {
        graphicsLayer { translationY = (scrollOffset() * factor).coerceIn(0f, maxShift.toPx()) }
    }
