package ru.vs.convention.analyze

import io.gitlab.arturbosch.detekt.Detekt

/**
 * Настройка detekt для проверки build-logic директории. Создает таску detektBuildLogic.
 * Такой костыль меньшее зло чем дублирование конфигурации detekt из ru.vs.convention.analyze.detekt для build-logic.
 */

plugins {
    id("ru.vs.convention.analyze.detekt")
}

// Дополнительная проверка места подключения плагина.
check(project == rootProject) { "This plugin allowed to apply only to root project" }

tasks.register<Detekt>("detektBuildLogic") {
    source = fileTree(project.projectDir) {
        include("build-logic/src/**/*")
        include("build-logic/build.gradle.kts")
        include("build-logic/settings.gradle.kts")
    }
}
