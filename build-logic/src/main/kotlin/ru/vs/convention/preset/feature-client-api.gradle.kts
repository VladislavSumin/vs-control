package ru.vs.convention.preset

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
