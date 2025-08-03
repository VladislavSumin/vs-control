plugins {
    id("ru.vs.convention.kmp.ktor")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // TODO поменять на impl все что возможно
            api(libs.vs.core.logger.api)
            api(libs.vs.core.di)
            api(projects.core.ktor.server)
            api(projects.core.serialization.protobuf)

            implementation(projects.feature.auth.serverImpl)
            implementation(projects.feature.entities.serverImpl)
            implementation(projects.feature.rsub.serverImpl)
            implementation(projects.feature.serverInfo.serverImpl)

            // TODO не поддерживается ios
            // api(libs.ktor.network.tlsCertificates)
            api(libs.ktor.server.cio)
            api(libs.ktor.server.contentNegotiation)
            api(libs.ktor.core.serialization.protobuf)

            api(libs.logging.slf4j)
            api(libs.logging.log4j.slf4j)
        }
    }
}
