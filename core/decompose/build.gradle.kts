plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.compose)
            api(libs.decompose.core)
            api(libs.decompose.extensions.compose)
        }
        androidMain.dependencies {
            api(libs.decompose.extensions.android)
        }
    }
}
