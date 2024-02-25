plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.navigationRootScreen.clientApi)
            implementation(projects.feature.navigationRootScreen.clientApi)
            implementation(projects.feature.rootContentScreen.clientApi)
        }
    }
}
