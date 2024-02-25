plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        all {
            languageSettings {
                optIn("ru.vs.core.navigation.NavigationInternalApi")
            }
        }
        commonMain.dependencies {
            api(projects.core.navigation.screen.api)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.logger.api)
        }
    }
}
