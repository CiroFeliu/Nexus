package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.i18n.LanguageSwitcher

@Composable
fun PortfolioShell(onLanguageSelected: (Language) -> Unit) {
    // The switcher sits outside the scrollable content below, so it stays visible regardless
    // of scroll position instead of scrolling away with the sections.
    Column(modifier = Modifier.fillMaxSize()) {
        LanguageSwitcher(onLanguageSelected = onLanguageSelected)
        Column(modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())) {
            PortfolioSection.entries.forEach { section ->
                section.content()
            }
        }
    }
}
