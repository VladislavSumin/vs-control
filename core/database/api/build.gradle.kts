plugins {
    id("ru.vs.convention.kmp.all")
    id("app.cash.sqldelight")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.sqldelight.coroutines)
        }
    }
}
