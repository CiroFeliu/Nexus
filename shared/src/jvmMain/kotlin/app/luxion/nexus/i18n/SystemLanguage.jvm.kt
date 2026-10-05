package app.luxion.nexus.i18n

import java.util.Locale

actual fun systemLanguageTag(): String = Locale.getDefault().language
