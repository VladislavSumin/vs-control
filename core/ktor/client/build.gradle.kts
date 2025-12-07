plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.client.core)
            // TODO переписать на конфигурацию снаружи модуля.
            implementation(libs.ktor.client.websocket)
            // TODO переписать на конфигурацию снаружи модуля.
            implementation(libs.ktor.client.contentNegotiation)
            // TODO переписать на конфигурацию снаружи модуля.
            implementation(libs.ktor.core.serialization.protobuf)
            implementation(libs.ktor.client.cio)
            implementation(vsCoreLibs.vs.core.di)
        }
    }
}
