package ru.vs.convention.preset

/**
 * Базовый пресет для всех client-impl feature модулей с добавлением ui зависимостей.
 * Содержит базовые зависимости и настройки характерные для всех client feature содержащих ui.
 */

plugins {
    id("ru.vs.convention.preset.feature-client-api-ui")
    id("ru.vs.convention.preset.feature-client-impl")
    id("ru.vs.convention.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:compose"))
            implementation(project(":core:navigation:impl"))
            implementation(project(":core:shared-element-transition"))
            implementation(project(":core:uikit:icons"))
        }
    }
}
