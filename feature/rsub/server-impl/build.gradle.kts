plugins {
    id("ru.vs.convention.preset.feature-server-impl")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.rsub.connector.ktorWebsocket.server)
        }
    }
}
