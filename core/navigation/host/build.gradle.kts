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
            implementation(projects.core.navigation.screen.impl)
            implementation(projects.core.decompose)
        }
    }
}
