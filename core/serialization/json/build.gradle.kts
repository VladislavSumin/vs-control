plugins {
    id("ru.vs.convention.kmp.all")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.serialization.core)
            api(vsCoreLibs.kotlin.serialization.json)
            implementation(vsCoreLibs.vs.core.di)
        }
    }
}
