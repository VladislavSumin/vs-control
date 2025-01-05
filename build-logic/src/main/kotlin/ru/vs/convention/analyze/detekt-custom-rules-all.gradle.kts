package ru.vs.convention.analyze

/**
 * Подключает кастомные правила detekt ко всем модулям
 */

plugins {
    id("ru.vladislavsumin.convention.analyze.detekt")
}

check(project === rootProject) { "This convention may be applied only to root project" }

allprojects {
    dependencies {
        // Добавляет проверку форматирования кода.
        detektPlugins(project(":custom-detekt-rules"))
    }
}
