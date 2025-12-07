plugins {
    id("ru.vs.convention.preset.feature-client-impl-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(vsCoreLibs.vs.core.navigation.debug)
        }
    }
}
