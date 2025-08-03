plugins {
    id("ru.vs.convention.kmp.ktor")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.server.core)
            api(libs.ktor.server.websocket)
            implementation(libs.vs.core.di)
        }
    }
}
