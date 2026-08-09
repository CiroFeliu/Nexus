package app.luxion.nexus.navigation

import androidx.compose.runtime.Composable
import app.luxion.nexus.i18n.Language
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.SkillsSection
import nexus.shared.generated.resources.Res
import nexus.shared.generated.resources.hero_photo
import org.jetbrains.compose.resources.painterResource

enum class PortfolioSection(val label: Map<Language, String>, val content: @Composable () -> Unit) {
    HeroAbout(
        label = mapOf(Language.English to "About", Language.Spanish to "Sobre mí"),
        content = { HeroAboutSection.Content(photo = painterResource(Res.drawable.hero_photo)) },
    ),
    Skills(
        label = mapOf(Language.English to "Skills", Language.Spanish to "Habilidades"),
        content = { SkillsSection.Content() },
    ),
    Experience(
        label = mapOf(Language.English to "Experience", Language.Spanish to "Experiencia"),
        content = { ExperienceSection.Content() },
    ),
    Projects(
        label = mapOf(Language.English to "Projects", Language.Spanish to "Proyectos"),
        content = { ProjectsSection.Content() },
    ),
    Contact(
        label = mapOf(Language.English to "Contact", Language.Spanish to "Contacto"),
        content = { ContactSection.Content() },
    ),
}
