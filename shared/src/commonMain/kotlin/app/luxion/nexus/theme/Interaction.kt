package app.luxion.nexus.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp

private const val PRESSED_SCALE = 0.98f

@Composable
fun Modifier.interactive(
    interactionSource: MutableInteractionSource,
    focusShape: Shape = PortfolioShapeRoles.interactive,
): Modifier {
    val pressed by interactionSource.collectIsPressedAsState()
    val focused by interactionSource.collectIsFocusedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) PRESSED_SCALE else 1f,
        animationSpec = motionSpec(PortfolioMotion.feedback()),
        label = "pressScale",
    )
    val focusRing = if (focused) {
        Modifier.border(2.dp, MaterialTheme.colorScheme.primary, focusShape)
    } else {
        Modifier
    }
    return this
        .pointerHoverIcon(PointerIcon.Hand)
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .then(focusRing)
}
