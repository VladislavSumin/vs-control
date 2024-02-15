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
    // TODO request feature to define configurations as regexp
    configurations += setOf("commonMainImplementation", "commonMainApi")

    allowed = arrayOf(
        // Общие для всех проектов настройки
        ".* -> :core:.*",

        // Настройки специфичные для Control
        ":feature:.*:client-impl -> :feature:.*:client-api",
        ":client:.* -> :client:common",
        ":client:common -> :feature:.*:client-impl",
    )
}
