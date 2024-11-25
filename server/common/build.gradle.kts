plugins {
    id("ru.vs.convention.kmp.ktor")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // TODO поменять на impl все что возможно
            api(projects.core.logger.api)
            api(projects.core.di)
            api(projects.core.ktor.server)
            api(projects.core.serialization.protobuf)

            implementation(projects.feature.serverInfo.serverImpl)

            api(libs.ktor.network.tlsCertificates)
            // TODO api(libs.ktor.server.cio) cio не поддерживает ssl
            api(libs.ktor.server.netty)
            api(libs.ktor.server.contentNegotiation)
            api(libs.ktor.server.serialization.protobuf)

            api(libs.logging.slf4j)
            api(libs.logging.log4j.slf4j)
        }
    }
}
