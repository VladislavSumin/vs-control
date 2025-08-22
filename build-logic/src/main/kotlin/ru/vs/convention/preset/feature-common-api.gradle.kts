package ru.vs.convention.preset

import ru.vs.utils.libs

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("ru.vs.convention.kotlin-context-parameters")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.vs.core.logger.api)
            implementation(project(":core:coroutines"))
            implementation(project(":core:serialization:protobuf"))
            implementation(project(":core:utils"))
        }
    }
}
