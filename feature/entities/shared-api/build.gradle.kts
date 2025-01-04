plugins {
    id("ru.vs.convention.preset.feature-shared-api")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.id)
        }
    }
}
