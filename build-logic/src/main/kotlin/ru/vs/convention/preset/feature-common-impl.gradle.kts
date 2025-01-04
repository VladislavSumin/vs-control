package ru.vs.convention.preset

plugins {
    id("ru.vs.convention.preset.feature-common-api")
    id("ru.vs.convention.impl-to-api-dependency")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:di"))
        }
    }
}
