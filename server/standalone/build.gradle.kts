import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("ru.vladislavsumin.convention.kmp.jvm")
}

kotlin {
    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set("ru.vs.control.server.MainKt")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.server.common)
            implementation(vsCoreLibs.vs.core.logger.platform)
        }
    }
}
