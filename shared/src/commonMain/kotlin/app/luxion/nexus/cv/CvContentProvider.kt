package app.luxion.nexus.cv

import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.entries
import app.luxion.nexus.navigation.sections.skillCategories

// Builds the CV content model from the same section data the portfolio renders. This is the
// only place that maps portfolio content into CV shape — see openspec/changes/
// add-dynamic-cv-export/design.md for why no content is duplicated into a separate file.
fun buildCvContent(): CvContent = CvContent(
    name = HeroAboutSection.NAME,
    role = HeroAboutSection.ROLE,
    bio = HeroAboutSection.BIO,
    skillCategories = skillCategories.map { CvContent.SkillCategory(it.label, it.skills) },
    experience = entries.map {
        CvContent.ExperienceEntry(
            role = it.role,
            company = it.company,
            dateRange = it.dateRange,
            description = it.description,
        )
    },
    projects = ProjectsSection.projects.map {
        CvContent.ProjectEntry(
            company = it.company,
            name = it.name,
            description = it.description,
            techStack = it.techStack,
            link = it.link,
            unavailableNote = it.unavailableNote,
        )
    },
    contactLinks = ContactSection.links.map { CvContent.ContactLink(it.label, it.url) },
)
