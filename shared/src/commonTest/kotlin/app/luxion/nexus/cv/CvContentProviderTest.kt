package app.luxion.nexus.cv

import app.luxion.nexus.i18n.Language
import app.luxion.nexus.navigation.sections.ContactSection
import app.luxion.nexus.navigation.sections.ExperienceSection
import app.luxion.nexus.navigation.sections.HeroAboutSection
import app.luxion.nexus.navigation.sections.ProjectsSection
import app.luxion.nexus.navigation.sections.entries
import app.luxion.nexus.navigation.sections.skillCategories
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

// `buildCvContent` is plain Kotlin logic (no composables, no platform APIs), unlike
// `CvExport`'s per-platform `actual`s — which stay untested, consistent with this change's
// original non-goal of not testing platform actuals that call real platform APIs.
class CvContentProviderTest {

    @Test
    fun buildsHeroFieldsFromHeroAboutSection() {
        val hero = HeroAboutSection.content.getValue(Language.English)

        val content = buildCvContent(Language.English)

        assertEquals(hero.name, content.name)
        assertEquals(hero.role, content.role)
        assertEquals(hero.bio, content.bio)
    }

    @Test
    fun includesEverySkillCategoryExperienceProjectAndContactLink() {
        val content = buildCvContent(Language.English)

        assertEquals(skillCategories.size, content.skillCategories.size)
        assertEquals(entries.size, content.experience.size)
        assertEquals(ProjectsSection.projects.size, content.projects.size)
        assertEquals(ContactSection.links.size, content.contactLinks.size)
    }

    @Test
    fun translatesContentButKeepsUntranslatedFieldsStable() {
        val english = buildCvContent(Language.English)
        val spanish = buildCvContent(Language.Spanish)

        // The person's name and company names aren't translated, unlike role/bio/description —
        // same "stable anchors over exact copy" split the section composable tests use.
        assertEquals(english.name, spanish.name)
        assertEquals(english.experience.map { it.company }, spanish.experience.map { it.company })
        assertNotEquals(english.role, spanish.role)
        assertNotEquals(english.bio, spanish.bio)
    }
}
