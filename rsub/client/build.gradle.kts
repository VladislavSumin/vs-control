plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.serialization.json")
    id("kotlinx-atomicfu")
}

atomicfu {
    // TODO remove when atomicfu updates
    // atomicfu 0.22.0 crashes when compiling jvm
    // with java 21.
    transformJvm = false
}

android {
    namespace = "ru.vs.rsub.client"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.core)

                implementation(coreLibs.kotlin.atomicfu)
                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.logging)
            }
        }
    }
}
