package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.theme.PortfolioContentContainer
import app.luxion.nexus.theme.PortfolioSpacing

object PortfolioFooter {
    private val copyrightLine = mapOf(
        Language.English to "© 2026 Ciro Feliu. All rights reserved.",
        Language.Spanish to "© 2026 Ciro Feliu. Todos los derechos reservados.",
    )
    private val buildNote = mapOf(
        Language.English to "Built with Compose Multiplatform, shared across web, Android, desktop, and iOS.",
        Language.Spanish to "Construido con Compose Multiplatform, compartido entre web, Android, escritorio e iOS.",
    )

    @Composable
    fun Content() {
        val language = LocalAppLanguage.current
        PortfolioContentContainer(verticalPadding = PaddingValues(bottom = PortfolioSpacing.extraLarge)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraSmall),
            ) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(PortfolioSpacing.large))
                Text(
                    text = copyrightLine.getValue(language),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = buildNote.getValue(language),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
