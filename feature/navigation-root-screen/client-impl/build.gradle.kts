plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.navigation.host)
            implementation(projects.feature.welcomeScreen.clientApi)
        }
    }
}
