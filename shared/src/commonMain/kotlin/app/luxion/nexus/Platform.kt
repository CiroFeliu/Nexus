package app.luxion.nexus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform