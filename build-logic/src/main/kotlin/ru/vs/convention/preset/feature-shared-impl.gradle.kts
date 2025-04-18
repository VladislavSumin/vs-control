package ru.vs.convention.preset

plugins {
    id("ru.vs.convention.preset.feature-shared-api")
    id("ru.vs.convention.preset.feature-common-impl")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":rsub:core"))
        }
    }
}
