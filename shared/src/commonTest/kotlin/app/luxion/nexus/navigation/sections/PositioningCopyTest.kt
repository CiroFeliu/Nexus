package app.luxion.nexus.navigation.sections

import app.luxion.nexus.documentTitle
import app.luxion.nexus.i18n.Language
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class PositioningCopyTest {

    private val retiredTitles = listOf("Architect", "Arquitecto", "Senior Developer", "Desarrollador Senior")

    private val freelance = entries.single { it.company == "Freelance" }

    @Test
    fun heroStatesTheHeadlineInEnglish() {
        val english = HeroAboutSection.content.getValue(Language.English)
        assertEquals("Product engineer", english.role)
        assertEquals("I build with AI agents in parallel, from spec to real device.", english.intro)
    }

    @Test
    fun heroStatesTheHeadlineInSpanish() {
        val spanish = HeroAboutSection.content.getValue(Language.Spanish)
        assertEquals("Ingeniero de producto", spanish.role)
        assertEquals("Desarrollo con agentes de IA en paralelo, de la spec al dispositivo real.", spanish.intro)
    }

    @Test
    fun documentTitleUsesTheProductEngineerTitle() {
        assertEquals("Ciro Feliu · Product engineer", documentTitle(Language.English))
        assertEquals("Ciro Feliu · Ingeniero de producto", documentTitle(Language.Spanish))
    }

    @Test
    fun freelanceRoleStaysInEnglishWithTranslatedDescriptions() {
        val english = freelance.content.getValue(Language.English)
        val spanish = freelance.content.getValue(Language.Spanish)
        assertEquals("Contract engineer for AI-first product teams", english.role)
        assertEquals(english.role, spanish.role)
        assertNotEquals(english.description, spanish.description)
    }

    @Test
    fun noRetiredTitleRemainsInHeroExperienceOrDocumentTitle() {
        val strings = Language.entries.flatMap { language ->
            val hero = HeroAboutSection.content.getValue(language)
            listOf(hero.role, hero.intro, hero.bio, documentTitle(language)) +
                entries.flatMap { entry -> entry.content.getValue(language).let { listOf(it.role, it.description) } }
        }
        val offending = strings.filter { text -> retiredTitles.any { it in text } }
        assertTrue(offending.isEmpty(), "Retired titles still present: $offending")
    }
}
