plugins {
    id("ru.vs.convention.kmp.ktor")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.rsub.core)

            implementation(projects.core.coroutines)
            implementation(projects.core.logger.api)
            implementation(libs.kotlin.serialization.json)
        }
    }
}
