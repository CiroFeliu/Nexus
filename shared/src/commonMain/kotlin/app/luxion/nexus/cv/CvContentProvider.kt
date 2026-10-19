package app.luxion.nexus.cv

import app.luxion.nexus.i18n.Language
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.entries
import app.luxion.nexus.navigation.sections.skillCategories

fun buildCvContent(language: Language): CvContent {
    val hero = HeroAboutSection.content.getValue(language)
    return CvContent(
        name = hero.name,
        role = hero.role,
        bio = hero.bio,
        skillCategories = skillCategories.map {
            CvContent.SkillCategory(it.label.getValue(language), it.skills)
        },
        experience = entries.map {
            val content = it.content.getValue(language)
            CvContent.ExperienceEntry(
                role = content.role,
                company = it.company,
                dateRange = it.dateRange(language),
                description = content.description,
            )
        },
        projects = ProjectsSection.projects.map {
            val content = it.content.getValue(language)
            CvContent.ProjectEntry(
                company = it.company,
                name = content.name,
                description = content.description,
                techStack = it.techStack,
                link = it.link,
                unavailableNote = content.unavailableNote,
            )
        },
        contactLinks = ContactSection.links.map {
            CvContent.ContactLink(it.label.getValue(language), it.url)
        },
    )
}
