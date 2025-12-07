plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(vsCoreLibs.vs.core.di)
        }
    }
}
