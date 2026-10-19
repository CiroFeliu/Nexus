package app.luxion.nexus.theme.motion

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.luxion.nexus.theme.LocalReducedMotion
import app.luxion.nexus.theme.PortfolioMotion
import app.luxion.nexus.theme.motionSpec

private val HOVER_LIFT = 4.dp

@Composable
fun Modifier.spotlightBorder(
    shape: Shape,
    color: Color,
    width: Dp = 1.5.dp,
    radius: Dp = 180.dp,
): Modifier {
    if (LocalReducedMotion.current) return this
    val pointer = remember { mutableStateOf(Offset.Unspecified) }
    return this
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    val change = event.changes.firstOrNull() ?: continue
                    if (change.type != PointerType.Mouse) continue
                    pointer.value = if (event.type == PointerEventType.Exit) Offset.Unspecified else change.position
                }
            }
        }
        .drawWithCache {
            val outline = shape.createOutline(size, layoutDirection, this)
            val stroke = Stroke(width.toPx())
            val radiusPx = radius.toPx()
            val fade = color.copy(alpha = 0f)
            onDrawWithContent {
                drawContent()
                val position = pointer.value
                if (position.isSpecified) {
                    val brush = Brush.radialGradient(listOf(color, fade), center = position, radius = radiusPx)
                    drawOutline(outline = outline, brush = brush, style = stroke)
                }
            }
        }
}

@Composable
fun Modifier.hoverLift(interactionSource: MutableInteractionSource): Modifier {
    val hovered by interactionSource.collectIsHoveredAsState()
    val lift = animateFloatAsState(
        targetValue = if (hovered) 1f else 0f,
        animationSpec = motionSpec(PortfolioMotion.feedback()),
        label = "hoverLift",
    )
    return graphicsLayer { translationY = -lift.value * HOVER_LIFT.toPx() }
}
