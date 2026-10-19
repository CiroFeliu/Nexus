package app.luxion.nexus

import app.luxion.nexus.i18n.Language
import web.dom.ElementId
import web.dom.document

private val appLoaderId = ElementId("app-loader")

actual fun applyDocumentLanguage(language: Language, title: String) {
    document.documentElement.lang = language.tag
    document.title = title
}

actual fun notifyAppReady() {
    document.getElementById(appLoaderId)?.remove()
}
