package ru.vs.convention.preset

/**
 * Базовый пресет для всех client-impl feature модулей.
 * Содержит базовые зависимости и настройки характерные для всех client feature.
 */

plugins {
    id("ru.vs.convention.preset.feature-client-api")
    id("ru.vs.convention.atomicfu")
    id("ru.vs.convention.factory-generator")
    id("ru.vs.convention.impl-to-api-dependency")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:di"))
        }
    }
}
