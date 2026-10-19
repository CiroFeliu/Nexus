package app.luxion.nexus.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioMotion
import app.luxion.nexus.theme.PortfolioShapeRoles
import app.luxion.nexus.theme.interactive
import app.luxion.nexus.theme.motionSpec
import kotlinx.coroutines.launch

private val LINK_HORIZONTAL_PADDING = 12.dp
private val LINK_VERTICAL_PADDING = 10.dp
private val UNDERLINE_THICKNESS = 2.dp
private val UNDERLINE_INSET = 4.dp

private data class LinkBounds(val start: Float, val width: Float)

@Composable
fun SectionNavigation(
    activeSection: PortfolioSection?,
    onSectionSelected: (PortfolioSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val language = LocalAppLanguage.current
    val density = LocalDensity.current
    val linkPadding = with(density) { LINK_HORIZONTAL_PADDING.toPx() }
    val bounds = remember { mutableStateMapOf<PortfolioSection, LinkBounds>() }
    val underlineStart = remember { Animatable(0f) }
    val underlineWidth = remember { Animatable(0f) }
    val target = activeSection?.let { bounds[it] }
    val spec = motionSpec(PortfolioMotion.feedback<Float>())
    LaunchedEffect(target) {
        val destination = target ?: LinkBounds(underlineStart.value, 0f)
        if (underlineWidth.value == 0f) {
            underlineStart.snapTo(destination.start)
            underlineWidth.snapTo(destination.width)
        } else {
            launch { underlineStart.animateTo(destination.start, spec) }
            launch { underlineWidth.animateTo(destination.width, spec) }
        }
    }
    val underlineColor = MaterialTheme.colorScheme.primary
    Row(
        modifier = modifier.drawWithContent {
            drawContent()
            val width = underlineWidth.value
            if (width > 0f) {
                drawRect(
                    color = underlineColor,
                    topLeft = Offset(underlineStart.value, size.height - (UNDERLINE_INSET + UNDERLINE_THICKNESS).toPx()),
                    size = Size(width, UNDERLINE_THICKNESS.toPx()),
                )
            }
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PortfolioSection.entries.forEach { section ->
            NavLink(
                label = section.label.getValue(language),
                isActive = section == activeSection,
                onClick = { onSectionSelected(section) },
                modifier = Modifier.onPlaced { coordinates ->
                    bounds[section] = LinkBounds(
                        start = coordinates.positionInParent().x + linkPadding,
                        width = coordinates.size.width - 2 * linkPadding,
                    )
                },
            )
        }
    }
}

@Composable
private fun NavLink(label: String, isActive: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val color by animateColorAsState(
        targetValue = if (isActive || hovered) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = motionSpec(PortfolioMotion.transition()),
        label = "navLinkColor",
    )
    Box(
        modifier = modifier
            .interactive(interactionSource)
            .clip(PortfolioShapeRoles.interactive)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = LINK_HORIZONTAL_PADDING, vertical = LINK_VERTICAL_PADDING),
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = color)
    }
}

internal fun deriveActiveSection(scrollValue: Int, sectionOffsets: Map<PortfolioSection, Int>): PortfolioSection? =
    PortfolioSection.entries
        .filter { section -> (sectionOffsets[section] ?: return@filter false) <= scrollValue }
        .maxByOrNull { section -> sectionOffsets.getValue(section) }
