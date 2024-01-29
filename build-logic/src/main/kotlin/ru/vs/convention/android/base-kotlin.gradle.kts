package ru.vs.convention.android

import ru.vs.configuration.projectConfiguration
import ru.vs.utils.android
import ru.vs.utils.kotlinOptions

/**
 * Расширение базовой android настройки, включает в себя настройку kotlin.
 */

plugins {
    id("ru.vs.convention.android.base")
    kotlin("android")
}

android {
    kotlinOptions {
        jvmTarget = project.projectConfiguration.core.jvmVersion
    }
}
