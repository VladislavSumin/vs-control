plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.splash)
            implementation(projects.feature.initialization.clientApi)
            implementation(projects.feature.initializedRootScreen.clientApi)
            implementation(projects.feature.splashScreen.clientApi)
        }
    }
}
