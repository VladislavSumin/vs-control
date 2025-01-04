plugins {
    id("ru.vs.convention.preset.feature-client-api-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.servers.clientApi)
        }
    }
}
