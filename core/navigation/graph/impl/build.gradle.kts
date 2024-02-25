plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.navigation.screen.impl)

            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.logger.api)
        }
    }
}
