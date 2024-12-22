plugins {
    id("ru.vs.convention.kmp.all")
}

android {
    namespace = "ru.vs.rsub.connector.ktor_websocket.client"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.connector.ktorWebsocket.core)

                implementation(projects.rsub.client)
                implementation(coreLibs.ktor.client.websocket)
            }
        }
    }
}
