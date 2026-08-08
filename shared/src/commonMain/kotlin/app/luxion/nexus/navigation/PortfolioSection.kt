package app.luxion.nexus.navigation

import androidx.compose.runtime.Composable
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.SkillsSection

enum class PortfolioSection(val content: @Composable () -> Unit) {
    HeroAbout({ HeroAboutSection.Content() }),
    Skills({ SkillsSection.Content() }),
    Experience({ ExperienceSection.Content() }),
    Projects({ ProjectsSection.Content() }),
    Contact({ ContactSection.Content() }),
}
