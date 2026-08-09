package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LanguageSwitcher

@Composable
fun PortfolioShell(onLanguageSelected: (Language) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        LanguageSwitcher(onLanguageSelected = onLanguageSelected)
        Column(modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())) {
            PortfolioSection.entries.forEachIndexed { index, section ->
                val sectionColor = if (index % 2 == 0) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.surface
                }
                Surface(modifier = Modifier.fillMaxWidth(), color = sectionColor) {
                    section.content()
                }
            }
        }
    }
}
