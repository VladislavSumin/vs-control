package ru.vs.convention.preset

import ru.vladislavsumin.utils.vsCoreLibs

plugins {
    id("ru.vs.convention.preset.feature-common-api")
    id("ru.vladislavsumin.convention.impl-to-api-dependency")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(vsCoreLibs.vs.core.di)
        }
    }
}
