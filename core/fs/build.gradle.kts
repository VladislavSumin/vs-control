plugins {
    id("ru.vs.convention.kmp.all")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.io.core)
            implementation(projects.core.di)
        }
    }
}
