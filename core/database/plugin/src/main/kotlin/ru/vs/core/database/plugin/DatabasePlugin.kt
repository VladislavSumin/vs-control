package ru.vs.core.database.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class DatabasePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register<GenerateDatabaseQueriesProvidersTask>("generateDatabaseQueriesProvider") {

        }
    }
}
