package ru.vs.utils

import org.gradle.api.Project

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
