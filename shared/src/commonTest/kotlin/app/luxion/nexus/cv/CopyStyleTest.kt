package app.luxion.nexus.cv

import app.luxion.nexus.i18n.Language
import kotlin.test.Test
import kotlin.test.assertTrue

class CopyStyleTest {

    private val forbiddenDashes = listOf('—', '–')

    @Test
    fun cvContentAndPrintOutputContainNoEmOrEnDashes() {
        Language.entries.forEach { language ->
            val content = buildCvContent(language)
            val strings = listOf(content.name, content.role, content.bio) +
                content.skillCategories.flatMap { listOf(it.label) + it.skills } +
                content.experience.flatMap { listOf(it.role, it.company, it.dateRange, it.description) } +
                content.projects.flatMap {
                    listOfNotNull(it.company, it.name, it.description, it.unavailableNote) + it.techStack
                } +
                content.contactLinks.map { it.label } +
                content.toPrintDocument()
            val offending = strings.filter { text -> forbiddenDashes.any { it in text } }
            assertTrue(offending.isEmpty(), "Dashes found in ${language.name} copy: $offending")
        }
    }
}
