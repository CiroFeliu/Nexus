package app.luxion.nexus

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.luxion.nexus.navigation.PortfolioShell
import app.luxion.nexus.theme.PortfolioTheme

@Composable
@Preview
fun App() {
    PortfolioTheme {
        // Paints the active color scheme's background/content color onto the window or
        // canvas — without this, the app renders on its platform's default (white) surface
        // regardless of which MaterialTheme color scheme is active.
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PortfolioShell()
        }
    }
}
