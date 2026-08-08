package app.luxion.nexus.navigation

import androidx.compose.runtime.Composable
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.SkillsSection

// The portfolio's sections, in display order. This is the only place section order is
// defined; the shell just iterates `entries` and renders each one's `content`. Adding or
// reordering a section only requires editing this list, never the shell itself.
enum class PortfolioSection(val title: String, val content: @Composable () -> Unit) {
    HeroAbout(HeroAboutSection.title, { HeroAboutSection.Content() }),
    Skills(SkillsSection.title, { SkillsSection.Content() }),
    Experience(ExperienceSection.title, { ExperienceSection.Content() }),
    Projects(ProjectsSection.title, { ProjectsSection.Content() }),
    Contact(ContactSection.title, { ContactSection.Content() }),
}
