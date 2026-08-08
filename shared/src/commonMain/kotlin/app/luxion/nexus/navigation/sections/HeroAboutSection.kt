package app.luxion.nexus.navigation.sections

import androidx.compose.runtime.Composable
import app.luxion.nexus.navigation.SectionPlaceholder

// Hero/About: introduction and personal summary. Filled in by a follow-up change.
object HeroAboutSection {
    const val title = "Hero / About"

    @Composable
    fun Content() {
        SectionPlaceholder(title)
    }
}
