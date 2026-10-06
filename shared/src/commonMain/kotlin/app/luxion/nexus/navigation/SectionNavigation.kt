package app.luxion.nexus.navigation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioSpacing
import app.luxion.nexus.theme.interactive

@Composable
fun SectionNavigation(
    activeSection: PortfolioSection?,
    onSectionSelected: (PortfolioSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val language = LocalAppLanguage.current
    FlowRow(
        modifier = modifier.padding(horizontal = PortfolioSpacing.large, vertical = PortfolioSpacing.small),
    ) {
        PortfolioSection.entries.forEach { section ->
            val isActive = section == activeSection
            val interactionSource = remember { MutableInteractionSource() }
            TextButton(
                onClick = { onSectionSelected(section) },
                interactionSource = interactionSource,
                modifier = Modifier.interactive(interactionSource),
            ) {
                Text(
                    text = section.label.getValue(language),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                    color = if (isActive) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }
    }
}

internal fun deriveActiveSection(scrollValue: Int, sectionOffsets: Map<PortfolioSection, Int>): PortfolioSection? =
    PortfolioSection.entries
        .filter { section -> (sectionOffsets[section] ?: return@filter false) <= scrollValue }
        .maxByOrNull { section -> sectionOffsets.getValue(section) }
