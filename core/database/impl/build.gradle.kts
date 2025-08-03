plugins {
    id("ru.vs.convention.kmp.all")
    id("app.cash.sqldelight")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.database.api)

            implementation(projects.core.coroutines)
            implementation(libs.vs.core.di)
        }

        androidMain.dependencies {
            implementation(libs.sqldelight.android)
        }
        jvmMain.dependencies {
            implementation(libs.sqldelight.sqlite)
        }
        nativeMain.dependencies {
            implementation(libs.sqldelight.native)
        }
        jsMain.dependencies {
            implementation(libs.sqldelight.sqljs)
        }
    }
}
