package ru.vs.convention.android

import ru.vs.utils.android

/**
 * Устанавливает базовый namespace для android модулей вида ru.vs.***, где *** заменяются на полное имя проекта.
 */

/**
 * Возвращает последовательность проектов от текущего включительно до корня включительно.
 */
fun Project.pathSequence(): Sequence<Project> {
    var project: Project = this
    return sequence {
        while (true) {
            yield(project)
            project = project.parent ?: break
        }
    }
}

/**
 * Возвращает полное имя проекта, используя "." как разделитель
 */
fun Project.fullName(): String {
    return pathSequence()
        .asIterable()
        .reversed()
        .drop(1) // отбрасываем root project
        .joinToString(separator = ".") {
            it.name.replace("-", "_")
        }
}

android {
    namespace = "ru.vs.${project.fullName()}"
}
