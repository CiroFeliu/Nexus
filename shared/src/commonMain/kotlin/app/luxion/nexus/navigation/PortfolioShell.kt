package app.luxion.nexus.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Renders every declared portfolio section, in order, inside one scrollable page — the
// single entry point every target's `App()` composes, with no per-target navigation code.
@Composable
fun PortfolioShell() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        PortfolioSection.entries.forEach { section ->
            section.content()
        }
    }
}
