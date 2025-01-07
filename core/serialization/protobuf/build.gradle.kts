plugins {
    id("ru.vs.convention.kmp.all")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.serialization.core)
            api(libs.kotlin.serialization.protobuf)
            implementation(projects.core.di)
        }
    }
}
