package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
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
        Column(
            modifier = Modifier.fillMaxWidth().padding(PortfolioSpacing.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PortfolioSpacing.extraSmall),
        ) {
            Text(
                text = copyrightLine.getValue(language),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Text(
                text = buildNote.getValue(language),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}
