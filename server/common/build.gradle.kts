plugins {
    id("ru.vs.convention.kmp.ktor")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.logger.api)
            api(projects.core.di)
            api(projects.core.serialization.protobuf)

            api(libs.ktor.server.core)
            api(libs.ktor.server.cio)
            api(libs.ktor.server.contentNegotiation)
            api(libs.ktor.server.serialization.protobuf)
        }
    }
}
