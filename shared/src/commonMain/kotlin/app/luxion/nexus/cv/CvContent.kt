package app.luxion.nexus.cv

// Plain data model for the exportable CV — see [buildCvContent] for how this is assembled
// from the same content the portfolio sections render, so the two can never drift apart.
data class CvContent(
    val name: String,
    val role: String,
    val bio: String,
    val skillCategories: List<SkillCategory>,
    val experience: List<ExperienceEntry>,
    val projects: List<ProjectEntry>,
    val contactLinks: List<ContactLink>,
) {
    data class SkillCategory(val label: String, val skills: List<String>)

    data class ExperienceEntry(
        val role: String,
        val company: String,
        val dateRange: String,
        val description: String,
    )

    data class ProjectEntry(
        val company: String,
        val name: String,
        val description: String,
        val techStack: List<String>,
        val link: String?,
        val unavailableNote: String?,
    )

    data class ContactLink(val label: String, val url: String)
}
