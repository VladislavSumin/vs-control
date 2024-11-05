plugins {
    id("ru.vs.convention.kmp.jvm")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.logger.api)

            api(libs.ktor.server.core)
            api(libs.ktor.server.cio)
        }
    }
}
