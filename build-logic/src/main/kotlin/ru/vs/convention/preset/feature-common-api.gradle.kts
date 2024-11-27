package ru.vs.convention.preset

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:coroutines"))
            implementation(project(":core:logger:api"))
            implementation(project(":core:serialization:protobuf"))
            implementation(project(":core:utils"))
        }
    }
}
