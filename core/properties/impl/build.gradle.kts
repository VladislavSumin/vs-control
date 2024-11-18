plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.properties.api)
            implementation(projects.core.coroutines)
            implementation(projects.core.di)

            implementation(libs.multiplatformSettings.core)
            implementation(libs.multiplatformSettings.coroutines)
        }
    }
}
