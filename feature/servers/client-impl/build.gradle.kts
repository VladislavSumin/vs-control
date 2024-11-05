plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.embeddedServer.clientApi)
            implementation(projects.feature.rootContentScreen.clientApi)
        }
    }
}
