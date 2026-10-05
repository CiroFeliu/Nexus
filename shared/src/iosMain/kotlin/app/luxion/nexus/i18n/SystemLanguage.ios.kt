package app.luxion.nexus.i18n

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun systemLanguageTag(): String = NSLocale.currentLocale.languageCode
