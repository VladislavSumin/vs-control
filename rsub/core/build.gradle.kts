plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
}

android {
    namespace = "ru.vs.rsub.core"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(coreLibs.vs.core.coroutines)
            }
        }
    }
}
