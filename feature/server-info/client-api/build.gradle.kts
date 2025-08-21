plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.feature.servers.clientApi)
        }
    }
}
