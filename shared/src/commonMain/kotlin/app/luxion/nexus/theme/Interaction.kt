package app.luxion.nexus.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val keyboardFocused by rememberKeyboardFocus(interactionSource)
    val scale by animateFloatAsState(
        targetValue = if (pressed) PRESSED_SCALE else 1f,
        animationSpec = motionSpec(PortfolioMotion.feedback()),
        label = "pressScale",
    )
    val focusRing = if (keyboardFocused) {
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

@Composable
private fun rememberKeyboardFocus(interactionSource: MutableInteractionSource) = remember(interactionSource) {
    mutableStateOf(false)
}.also { keyboardFocused ->
    LaunchedEffect(interactionSource) {
        var pointerActive = false
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    pointerActive = true
                    keyboardFocused.value = false
                }
                is PressInteraction.Release, is PressInteraction.Cancel -> Unit
                is FocusInteraction.Focus -> keyboardFocused.value = !pointerActive
                is FocusInteraction.Unfocus -> {
                    keyboardFocused.value = false
                    pointerActive = false
                }
            }
        }
    }
}
