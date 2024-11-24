package ru.vs.convention.preset

/**
 * Базовый пресет для всех client-impl feature модулей.
 * Содержит базовые зависимости и настройки характерные для всех client feature.
 */

plugins {
    id("ru.vs.convention.preset.feature-client-api")
    id("ru.vs.convention.preset.feature-shared-impl")
    id("ru.vs.convention.atomicfu")
    id("ru.vs.convention.factory-generator")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:autoload"))
            implementation(project(":core:di"))
            implementation(project(":core:properties:api"))
        }
    }
}
