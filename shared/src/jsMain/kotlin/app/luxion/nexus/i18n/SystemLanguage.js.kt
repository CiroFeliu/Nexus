package app.luxion.nexus.i18n

import web.navigator.navigator

actual fun systemLanguageTag(): String = navigator.language
