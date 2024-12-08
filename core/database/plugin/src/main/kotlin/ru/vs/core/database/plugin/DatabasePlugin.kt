package ru.vs.core.database.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class DatabasePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val taskProvider = project.tasks
            .register<GenerateDatabaseQueriesProvidersTask>("generateDatabaseQueriesProvider") {}

        val kotlinExt = project.extensions.getByName<KotlinMultiplatformExtension>("kotlin")
        kotlinExt.sourceSets.named("commonMain").configure {
            kotlin.srcDir(taskProvider.map { it.generatedCodeDir })
        }
    }
}
