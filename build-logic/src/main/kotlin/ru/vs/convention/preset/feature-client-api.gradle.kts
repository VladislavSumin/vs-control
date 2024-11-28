package ru.vs.convention.preset

import gradle.kotlin.dsl.accessors._652b3e3ec1cdd527452fa1ad16b3de59.kotlin
import gradle.kotlin.dsl.accessors._652b3e3ec1cdd527452fa1ad16b3de59.sourceSets

/**
 * Базовый пресет для всех client-api feature модулей.
 * Содержит базовые зависимости и настройки характерные для всех client feature.
 */

plugins {
    id("ru.vs.convention.platform-to-shared-dependency")
    id("ru.vs.convention.preset.feature-shared-api")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ktor:client"))
        }
    }
}
