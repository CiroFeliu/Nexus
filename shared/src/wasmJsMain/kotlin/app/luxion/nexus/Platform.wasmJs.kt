package app.luxion.nexus

import web.window.WindowTarget
import web.window._blank
import web.window.window

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun openUrl(url: String) {
    window.open(url, WindowTarget._blank)
}