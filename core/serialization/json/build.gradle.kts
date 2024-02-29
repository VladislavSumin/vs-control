plugins {
    id("ru.vs.convention.kmp.all")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.serialization.json)
            implementation(projects.core.di)
        }
    }
}
