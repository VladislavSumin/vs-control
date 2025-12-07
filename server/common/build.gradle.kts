plugins {
    id("ru.vs.convention.kmp.ktor")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // TODO поменять на impl все что возможно
            api(vsCoreLibs.vs.core.logger.api)
            api(vsCoreLibs.vs.core.di)
            api(vsCoreLibs.vs.core.serialization.protobuf)
            api(projects.core.ktor.server)

            implementation(projects.feature.auth.serverImpl)
            implementation(projects.feature.entities.serverImpl)
            implementation(projects.feature.rsub.serverImpl)
            implementation(projects.feature.serverInfo.serverImpl)

            // TODO не поддерживается ios
            // api(libs.ktor.network.tlsCertificates)
            api(libs.ktor.server.cio)
            api(libs.ktor.server.contentNegotiation)
            api(libs.ktor.core.serialization.protobuf)

            api(vsCoreLibs.logging.slf4j)
            api(vsCoreLibs.logging.log4j.slf4j)
        }
    }
}
