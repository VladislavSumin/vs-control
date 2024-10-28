plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.debugScreen.clientApi)
            implementation(projects.feature.navigationRootScreen.clientApi)

            // TODO временно пока нет экрана контента.
            implementation(projects.feature.debugScreen.clientApi)
        }
    }
}
