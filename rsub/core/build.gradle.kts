plugins {
    id("ru.vs.convention.kmp.all")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coroutines)
            implementation(vsCoreLibs.kotlin.serialization.protobuf)
        }
    }
}
