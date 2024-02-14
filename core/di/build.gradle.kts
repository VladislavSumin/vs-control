plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kodein.core)
        }
        androidMain.dependencies {
            api(libs.kodein.android)
        }
    }
}
