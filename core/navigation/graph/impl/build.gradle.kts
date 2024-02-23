plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.navigation.screen.impl)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.logger.api)
        }
    }
}
