package ru.vs.convention.preset

import gradle.kotlin.dsl.accessors._611a75feaf31951ac969a948928e6651.kotlin
import gradle.kotlin.dsl.accessors._611a75feaf31951ac969a948928e6651.sourceSets

plugins {
    id("ru.vs.convention.kmp.all")
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
