plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.navigation.screen.api)
            implementation(projects.core.decompose)
        }
    }
}
