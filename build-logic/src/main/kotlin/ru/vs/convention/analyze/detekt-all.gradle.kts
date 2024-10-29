package ru.vs.convention.analyze

/**
 * Настройка detekt плагина по умолчанию для всех модулей.
 */

plugins {
    id("ru.vs.convention.analyze.detekt-build-logic")
}

check(project === rootProject) { "This convention may be applied only to root project" }

allprojects {
    apply {
        plugin("ru.vs.convention.analyze.detekt")
    }
}
