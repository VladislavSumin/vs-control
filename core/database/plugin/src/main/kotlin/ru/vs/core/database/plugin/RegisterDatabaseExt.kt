package ru.vs.core.database.plugin

import app.cash.sqldelight.gradle.SqlDelightExtension
import org.gradle.api.Project
import org.gradle.api.internal.catalog.DelegatingProjectDependency

/**
 * Регистрирует [project] для импорта баз данных в общий хост.
 */
fun Project.registerDatabase(project: DelegatingProjectDependency) {
    evaluationDependsOn(project.dependencyProject.path)
    extensions.configure<SqlDelightExtension>("sqldelight") {
        databases.named("Database") {
            dependency(project)
        }
    }
}
