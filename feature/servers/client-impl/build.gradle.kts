plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
    id("ru.vs.convention.database.lib")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.auth.clientApi)
            implementation(projects.feature.embeddedServer.clientApi)
            implementation(projects.feature.serverInfo.clientApi)

            implementation(projects.rsub.connector.ktorWebsocket.client)
        }
    }
}
