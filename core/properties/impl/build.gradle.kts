plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.properties.api)
            implementation(projects.core.coroutines)
            implementation(vsCoreLibs.vs.core.di)
            implementation(projects.core.fs.api)

            implementation(libs.android.datastore.core)
            implementation(libs.android.datastore.preferences)
        }
    }
}
