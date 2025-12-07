package ru.vs.convention.preset

import ru.vladislavsumin.utils.vsCoreLibs

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("ru.vs.convention.kotlin-context-parameters")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(vsCoreLibs.vs.core.logger.api)
            implementation(vsCoreLibs.vs.core.serialization.protobuf)
            implementation(project(":core:coroutines"))
            implementation(project(":core:utils"))
        }
    }
}
