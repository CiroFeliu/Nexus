package app.luxion.nexus.i18n

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import app.luxion.nexus.theme.LocalPortfolioMonoTypography
import app.luxion.nexus.theme.PortfolioMotion
import app.luxion.nexus.theme.PortfolioShapeRoles
import app.luxion.nexus.theme.interactive
import app.luxion.nexus.theme.motionSpec

@Composable
fun LanguageSwitcher(
    onLanguageSelected: (Language) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalPortfolioMonoTypography.current.label,
) {
    val active = LocalAppLanguage.current
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Language.entries.forEachIndexed { index, language ->
            if (index > 0) {
                Text(text = "|", style = style, color = MaterialTheme.colorScheme.outline)
            }
            LanguageOption(
                label = language.tag.uppercase(),
                isActive = language == active,
                style = style,
                onClick = { onLanguageSelected(language) },
            )
        }
    }
}

@Composable
private fun LanguageOption(label: String, isActive: Boolean, style: TextStyle, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val color by animateColorAsState(
        targetValue = when {
            isActive -> MaterialTheme.colorScheme.primary
            hovered -> MaterialTheme.colorScheme.onSurface
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = motionSpec(PortfolioMotion.transition()),
        label = "languageColor",
    )
    Box(
        modifier = Modifier
            .interactive(interactionSource)
            .clip(PortfolioShapeRoles.interactive)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Button,
                onClick = onClick,
            )
            .semantics { selected = isActive }
            .padding(horizontal = 8.dp, vertical = 10.dp),
    ) {
        Text(text = label, style = style, color = color)
    }
}
