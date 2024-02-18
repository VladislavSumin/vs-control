plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.initialization.clientApi)
            implementation(projects.feature.splashScreen.clientApi)
        }
    }
}
