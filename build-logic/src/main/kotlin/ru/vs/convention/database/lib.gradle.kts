package ru.vs.convention.database

import ru.vs.utils.pathSequence

/**
 * Базовая настройка базы данных для библиотечного модуля.
 */

plugins {
    id("kotlin-multiplatform")
    id("app.cash.sqldelight")
}

sqldelight {
    databases {
        register("Database") {
            packageName.set("ru.vs.${project.fullName()}.repository")
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // TODO разделить бд на api/impl
            implementation(project(":core:database"))
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
