plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.debugScreen.clientApi)
            implementation(projects.feature.entitiesScreen.clientApi)
            implementation(projects.feature.servers.clientApi)
        }
    }
}
