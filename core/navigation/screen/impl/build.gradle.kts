plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.kmp.context-receivers")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.navigation.screen.api)
            implementation(projects.core.compose)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.logger.api)
        }
    }
}
