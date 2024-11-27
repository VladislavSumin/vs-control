package ru.vs.convention.preset

plugins {
    id("ru.vs.convention.preset.feature-server-api")
    id("ru.vs.convention.preset.feature-common-impl")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:di"))
            implementation(project(":core:ktor:server"))
        }
    }
}
