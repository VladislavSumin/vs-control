plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.fs.api)
            implementation(libs.vs.core.di)
        }
    }
}
