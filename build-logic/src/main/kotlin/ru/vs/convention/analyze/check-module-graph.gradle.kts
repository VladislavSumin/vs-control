package ru.vs.convention.analyze

/**
 * Настройки по умолчанию для Module Graph Assert.
 * Этот плагин проверяет корректность зависимостей между gradle модулями основываясь на простых регулярных выражениях.
 * Для проверки следует запустить assertModuleGraph.
 * Документация по плагину: https://github.com/jraska/modules-graph-assert.
 */

plugins {
    id("com.jraska.module.graph.assertion")
}

moduleGraphAssert {
    configurations += setOf("commonMainImplementation", "commonMainApi")

    allowed = arrayOf(
        // Общие для всех проектов настройки
        ".* -> :core:.*",

        // Настройки специфичные для Control
        ":feature:.*:client-impl -> :feature:.*:client-api",
        ":client -> :feature:.*:client-impl",
        ":server:embedded -> :server:common",
        ":server:standalone -> :server:common",
    )
}
