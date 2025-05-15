plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.mainScreen.clientApi)
            implementation(projects.feature.settingsScreen.clientApi)
            implementation(projects.feature.debugScreen.clientApi)
            implementation(projects.feature.embeddedServer.clientApi)
            implementation(projects.feature.servers.clientApi)
        }
    }
}
