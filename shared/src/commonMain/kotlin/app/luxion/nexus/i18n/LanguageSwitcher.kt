package app.luxion.nexus.i18n

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import app.luxion.nexus.theme.PortfolioSpacing

@Composable
fun LanguageSwitcher(onLanguageSelected: (Language) -> Unit, modifier: Modifier = Modifier) {
    val active = LocalAppLanguage.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = PortfolioSpacing.large, vertical = PortfolioSpacing.small),
        horizontalArrangement = Arrangement.End,
    ) {
        Language.entries.forEach { language ->
            TextButton(onClick = { onLanguageSelected(language) }) {
                Text(
                    text = language.tag.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (language == active) FontWeight.Bold else FontWeight.Normal,
                    color = if (language == active) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                )
            }
        }
    }
}
