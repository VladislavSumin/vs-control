plugins {
    id("ru.vs.convention.kmp.all")
    id("ru.vs.convention.atomicfu")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.rsub.core)

            implementation(projects.core.coroutines)
            implementation(projects.core.logger.api)
        }
    }
}
