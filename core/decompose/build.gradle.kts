plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.decompose.core)
            api(libs.decompose.extensions.compose)

            implementation(projects.core.compose)
            implementation(projects.core.utils)
        }
        androidMain.dependencies {
            api(libs.decompose.extensions.android)
        }
    }
}
