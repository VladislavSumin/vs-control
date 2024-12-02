plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.auth.clientApi)
            implementation(projects.feature.embeddedServer.clientApi)
            implementation(projects.feature.rootContentScreen.clientApi)
            implementation(projects.feature.serverInfo.clientApi)
            implementation(projects.feature.mainScreen.clientApi)
        }
    }
}
