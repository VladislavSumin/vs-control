package ru.vs.convention.preset

import ru.vs.utils.libs

plugins {
    id("ru.vs.convention.preset.feature-common-api")
    id("ru.vladislavsumin.convention.impl-to-api-dependency")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.vs.core.di)
        }
    }
}
