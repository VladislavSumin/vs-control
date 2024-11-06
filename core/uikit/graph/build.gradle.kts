plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.collections.tree)
            implementation(projects.core.compose)
        }
    }
}
