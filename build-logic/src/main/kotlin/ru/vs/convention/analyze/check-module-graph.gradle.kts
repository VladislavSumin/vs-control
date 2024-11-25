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
        ":feature:.*:server-impl -> :feature:.*:server-api",

        ":feature:.*:shared-impl -> :feature:.*:shared-api",

        ":feature:.*:client-api -> :feature:.*:shared-api",
        ":feature:.*:client-impl -> :feature:.*:shared-impl",

        ":feature:.*:server-api -> :feature:.*:shared-api",
        ":feature:.*:server-impl -> :feature:.*:shared-impl",

        ":client -> :feature:.*:client-impl",
        ":server -> :feature:.*:server-impl",

        ":server:embedded -> :server:common",
        ":server:standalone -> :server:common",
    )
}
