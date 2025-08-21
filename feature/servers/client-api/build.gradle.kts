plugins {
    id("ru.vs.convention.preset.feature-client-api-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.rsub.client)
        }
    }
}
