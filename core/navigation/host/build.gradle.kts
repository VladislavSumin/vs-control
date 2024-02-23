plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.navigation.screen.impl)
            implementation(projects.core.navigation.graph.api)

            implementation(projects.core.decompose)
        }
    }
}
