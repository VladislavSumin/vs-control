package ru.vs.convention.android

import ru.vs.utils.android
import ru.vs.utils.pathSequence

/**
 * Устанавливает базовый namespace для android модулей вида ru.vs.***, где *** заменяются на полное имя проекта.
 */

/**
 * Возвращает полное имя проекта, используя "." как разделитель
 */
fun Project.fullName(): String = pathSequence()
    .asIterable()
    .reversed()
    .drop(1) // отбрасываем root project
    .joinToString(separator = ".") {
        it.name.replace("-", "_")
    }

android {
    namespace = "ru.vs.${project.fullName()}"
}
