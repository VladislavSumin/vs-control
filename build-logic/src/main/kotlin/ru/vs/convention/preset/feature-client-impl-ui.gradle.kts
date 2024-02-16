package ru.vs.convention.preset

/**
 * Базовый пресет для всех client-impl feature модулей с добавлением ui зависимойтей.
 * Содержит базовые зависимости и настройки характерные для всех client feature содержащих ui.
 */

plugins {
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:compose"))
            implementation(project(":core:decompose"))
        }
    }
}
