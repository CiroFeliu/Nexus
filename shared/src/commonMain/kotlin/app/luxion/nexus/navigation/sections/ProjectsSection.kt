package app.luxion.nexus.navigation.sections

import androidx.compose.runtime.Composable
import app.luxion.nexus.navigation.SectionPlaceholder

// Projects: portfolio work with links to repos/demos. Filled in by a follow-up change.
object ProjectsSection {
    const val title = "Projects"

    @Composable
    fun Content() {
        SectionPlaceholder(title)
    }
}
