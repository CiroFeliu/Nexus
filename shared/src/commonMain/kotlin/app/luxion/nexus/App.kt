package app.luxion.nexus

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LocalAppLanguage
import app.luxion.nexus.i18n.persistLanguage
import app.luxion.nexus.i18n.readPersistedLanguage
import app.luxion.nexus.i18n.systemLanguageTag
import app.luxion.nexus.navigation.PortfolioShell
import app.luxion.nexus.theme.PortfolioTheme

@Composable
@Preview
fun App() {
    // Startup resolution order: persisted manual override, else system locale, else English.
    var language by remember { mutableStateOf(readPersistedLanguage() ?: Language.fromTag(systemLanguageTag())) }
    CompositionLocalProvider(LocalAppLanguage provides language) {
        PortfolioTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                PortfolioShell(
                    onLanguageSelected = { selected ->
                        language = selected
                        persistLanguage(selected)
                    },
                )
            }
        }
    }
}
