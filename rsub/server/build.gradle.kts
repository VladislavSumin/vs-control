plugins {
    id("ru.vs.convention.kmp.jvm")
    id("ru.vs.convention.kmp.linux")
    id("ru.vs.convention.kmp.macos")

    id("ru.vs.convention.serialization.json")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(projects.rsub.core)

                implementation(coreLibs.vs.core.coroutines)
                implementation(coreLibs.vs.core.logging)
            }
        }
    }
}
