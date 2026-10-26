package app.luxion.nexus.blog

import org.snakeyaml.engine.v2.api.Load
import org.snakeyaml.engine.v2.api.LoadSettings
import org.snakeyaml.engine.v2.exceptions.YamlEngineException
import java.time.LocalDate
import java.time.format.DateTimeParseException

data class ParsedPost(val meta: PostMeta, val body: String)

private const val DELIMITER = "---"

private val KNOWN_FIELDS = setOf("title", "date", "summary", "lang", "draft", "translationKey", "tags")

private val ISO_DATE = Regex("\\d{4}-\\d{2}-\\d{2}")

fun parsePost(location: String, text: String): ParsedPost {
    val lines = text.removePrefix("﻿").lines()
    val closing = lines.drop(1).indexOfFirst { it.trimEnd() == DELIMITER }
    if (lines.firstOrNull()?.trimEnd() != DELIMITER || closing < 0) {
        throw BlogContentException(listOf("$location: must start with a front matter block delimited by '---' lines"))
    }
    val yaml = lines.subList(1, closing + 1).joinToString("\n")
    val body = lines.drop(closing + 2).joinToString("\n")
    return ParsedPost(meta = parseFrontMatter(location, yaml), body = body)
}

fun parseFrontMatter(location: String, yaml: String): PostMeta {
    val loaded = try {
        Load(LoadSettings.builder().build()).loadFromString(yaml)
    } catch (exception: YamlEngineException) {
        throw BlogContentException(listOf("$location: front matter is not valid YAML (${exception.message?.lineSequence()?.first()})"))
    }
    val fields = loaded as? Map<*, *>
        ?: throw BlogContentException(listOf("$location: front matter must be a set of 'key: value' fields"))
    val reader = FieldReader(location, fields)
    reader.rejectUnknown()
    val title = reader.requiredText("title")
    val date = reader.requiredDate("date")
    val summary = reader.requiredText("summary")
    val lang = reader.requiredLanguage("lang")
    val draft = reader.optionalBoolean("draft") ?: false
    val translationKey = reader.optionalText("translationKey")
    val tags = reader.optionalTextList("tags")
    if (reader.problems.isNotEmpty()) throw BlogContentException(reader.problems)
    return PostMeta(
        title = title!!,
        date = date!!,
        summary = summary!!,
        lang = lang!!,
        draft = draft,
        translationKey = translationKey,
        tags = tags,
    )
}

private class FieldReader(private val location: String, private val fields: Map<*, *>) {
    val problems = mutableListOf<String>()

    fun rejectUnknown() {
        fields.keys.map { it.toString() }.filterNot { it in KNOWN_FIELDS }.forEach { key ->
            problems += "$location: unknown front matter field '$key' (allowed: ${KNOWN_FIELDS.joinToString()})"
        }
    }

    fun requiredText(key: String): String? = when (val value = fields[key]) {
        null -> missing(key)
        is String -> value.trim().ifEmpty { invalid(key, "must not be empty") }
        else -> invalid(key, "must be a text value")
    }

    fun optionalText(key: String): String? = if (key in fields) requiredText(key) else null

    fun requiredDate(key: String): LocalDate? {
        val value = fields[key] ?: return missing(key)
        val text = value.toString()
        if (value !is String || !ISO_DATE.matches(text)) return invalid(key, "must be an ISO date like 2026-11-03")
        return try {
            LocalDate.parse(text)
        } catch (_: DateTimeParseException) {
            invalid(key, "'$text' is not a real calendar date")
        }
    }

    fun requiredLanguage(key: String): PostLanguage? {
        val value = fields[key] ?: return missing(key)
        return PostLanguage.fromCode(value.toString())
            ?: invalid(key, "must be one of ${PostLanguage.entries.joinToString { it.code }}")
    }

    fun optionalBoolean(key: String): Boolean? = when (val value = fields[key]) {
        null -> if (key in fields) invalid(key, "must be true or false") else null
        is Boolean -> value
        else -> invalid(key, "must be true or false")
    }

    fun optionalTextList(key: String): List<String> {
        if (key !in fields) return emptyList()
        val items = fields[key] as? List<*> ?: return invalid(key, "must be a list like [kotlin, android]") ?: emptyList()
        val tags = items.mapNotNull { (it as? String)?.trim()?.takeIf(String::isNotEmpty) }
        if (tags.size != items.size) return invalid(key, "must only contain non-empty text values") ?: emptyList()
        return tags
    }

    private fun <T> missing(key: String): T? {
        problems += "$location: missing required field '$key'"
        return null
    }

    private fun <T> invalid(key: String, reason: String): T? {
        problems += "$location: field '$key' $reason"
        return null
    }
}
