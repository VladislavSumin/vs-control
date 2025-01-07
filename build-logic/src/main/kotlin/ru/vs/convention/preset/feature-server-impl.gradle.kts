package ru.vs.convention.preset

plugins {
    id("ru.vs.convention.preset.feature-server-api")
    id("ru.vs.convention.preset.feature-common-impl")
    id("ru.vs.convention.rsub-server-generator")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ktor:server"))
            implementation(project(":rsub:server"))
            implementation(project(":rsub:server-di"))
        }
    }
}
