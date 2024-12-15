package ru.vs.convention.database

import ru.vladislavsumin.utils.pathSequence

/**
 * Базовая настройка базы данных для библиотечного модуля.
 */

plugins {
    id("kotlin-multiplatform")
    id("app.cash.sqldelight")
    id("ru.vs.core.database")
}

sqldelight {
    databases {
        register("Database") {
            // TODO поичинить п
            packageName.set("ru.vs.${project.fullName()}.repository")
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:database:api"))
        }
    }
}

fun Project.fullName(): String = pathSequence()
    .asIterable()
    .reversed()
    .drop(1) // отбрасываем root project
    .joinToString(separator = ".") { it.name.dashToLowerCamelCase() }

private val dashRegex = "-[a-zA-Z]".toRegex()
fun String.dashToLowerCamelCase(): String {
    return dashRegex.replace(this) {
        it.value.replace("-", "").uppercase()
    }
}
