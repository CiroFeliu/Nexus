plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation(libs.commonmark)
    implementation(libs.commonmark.gfmTables)
    implementation(libs.commonmark.gfmStrikethrough)
    implementation(libs.snakeyaml.engine)
    testImplementation(libs.kotlin.testJunit)
}

tasks.test {
    systemProperty("nexus.repoRoot", rootDir.absolutePath)
}
