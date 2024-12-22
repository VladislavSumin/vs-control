plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.rsub.connector.ktor_websocket.core"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.core)
                implementation(coreLibs.ktor.websockets)
            }
        }
    }
}
