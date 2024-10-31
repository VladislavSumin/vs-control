plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.navigation.api)
            implementation(projects.core.collections.tree)
            implementation(projects.core.compose)
            implementation(projects.core.decompose)
            implementation(projects.core.di)
            implementation(projects.core.logger.api)
            implementation(projects.core.uikit.graph)
            implementation(projects.core.utils)
        }
    }
}
