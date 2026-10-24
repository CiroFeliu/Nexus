import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

val blogGenerator by configurations.creating {
    isCanBeConsumed = false
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        attribute(TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE, objects.named(TargetJvmEnvironment.STANDARD_JVM))
    }
}

dependencies {
    blogGenerator(project(":blogGenerator"))
}

val blogPostsDir = rootProject.layout.projectDirectory.dir("blog/posts")
val blogFontsDir = rootProject.layout.projectDirectory.dir("shared/src/commonMain/composeResources/font")
val blogOutputDir = layout.buildDirectory.dir("generated/blog")
val includeBlogDrafts = providers.gradleProperty("blog.drafts").map(String::toBoolean).orElse(false)

val generateBlog by tasks.registering(JavaExec::class) {
    group = "build"
    description = "Generates the static blog from blog/posts into the web app's resources."
    classpath = blogGenerator
    mainClass = "app.luxion.nexus.blog.MainKt"
    inputs.files(fileTree(blogPostsDir)).withPropertyName("posts").withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.files(fileTree(blogFontsDir)).withPropertyName("fonts").withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.property("includeDrafts", includeBlogDrafts)
    outputs.dir(blogOutputDir)
    args(
        "--posts", blogPostsDir.asFile.absolutePath,
        "--fonts", blogFontsDir.asFile.absolutePath,
        "--output", blogOutputDir.get().asFile.absolutePath,
        "--include-drafts", includeBlogDrafts.get().toString(),
    )
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared"))

            implementation(libs.compose.ui)
        }
        webMain {
            resources.srcDir(generateBlog)
        }
    }
}
