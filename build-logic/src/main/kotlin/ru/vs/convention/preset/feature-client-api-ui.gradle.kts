package ru.vs.convention.preset

import ru.vs.utils.libs

/**
 * Базовый пресет для всех client-api feature модулей с добавлением api ui зависимостей.
 * Содержит базовые зависимости и настройки характерные для всех client feature.
 */

plugins {
    id("ru.vs.convention.preset.feature-client-api")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:decompose"))
            implementation(libs.vs.core.navigation.api)
        }
    }
}
