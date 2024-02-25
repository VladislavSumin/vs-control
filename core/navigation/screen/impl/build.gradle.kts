plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.kmp.context-receivers")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.navigation.screen.api)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.logger.api)
        }
    }
}
