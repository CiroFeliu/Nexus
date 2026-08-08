package app.luxion.nexus.navigation.sections

import androidx.compose.runtime.Composable
import app.luxion.nexus.navigation.SectionPlaceholder

// Contact: ways to reach out (email, social, links). Filled in by a follow-up change.
object ContactSection {
    const val title = "Contact"

    @Composable
    fun Content() {
        SectionPlaceholder(title)
    }
}
