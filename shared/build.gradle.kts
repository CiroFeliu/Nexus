import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    jvm()
    
    js {
        browser()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    android {
       namespace = "app.luxion.nexus.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            // For FileProvider, used to share the generated CV PDF (CvExport.android.kt).
            implementation(libs.androidx.core.ktx)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.compose.uiTest)
        }
        jvmTest.dependencies {
            // Skiko's native rendering library for the host OS/arch — required at runtime by
            // `runComposeUiTest` on the JVM target, same as `:desktopApp` needs it to run.
            implementation(compose.desktop.currentOs)
        }
        jsMain.dependencies {
            implementation(libs.wrappers.browser)
        }
        wasmJsMain.dependencies {
            implementation(libs.wrappers.browser)
        }
        jvmMain.dependencies {
            // Text-based (not rasterized) PDF writing for the desktop CV export
            // (CvExport.jvm.kt) — see design.md's Open Questions for why PDFBox.
            implementation(libs.pdfbox)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}

// Kotlin Multiplatform doesn't register a task literally named `test` (it uses `allTests` plus
// one task per target, e.g. `jvmTest`), so the root `./gradlew test` — run from every subproject
// that has a task by that name — would otherwise skip `:shared` entirely. This alias makes the
// project's documented `./gradlew test` command actually run `:shared`'s test suite.
//
// Depends on `jvmTest` specifically, not `allTests`: the JVM target is the one `runComposeUiTest`
// is designed and verified against here (see design.md's "kotlin.test / runComposeUiTest"
// decision). `allTests` would also pull in the Android host test (needs an Android SDK) and the
// JS/Wasm browser tests (need a working karma+headless-Chrome setup and, in this Compose
// Multiplatform version, hit unrelated tooling failures under karma-webpack) — extra setup this
// change isn't meant to require, per its "no CI infrastructure" non-goal.
tasks.register("test") {
    group = "verification"
    description = "Runs :shared's JVM test suite."
    dependsOn("jvmTest")
}