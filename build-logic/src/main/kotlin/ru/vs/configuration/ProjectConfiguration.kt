package ru.vs.configuration

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType

/**
 * Файл пропертей проекта, содержит dsl для доступа к любым проперти используемым при сборке.
 */
@Suppress("UnnecessaryAbstractClass")
open class ProjectConfiguration(propertyProvider: PropertyProvider) :
    Configuration("ru.vs", propertyProvider) {
    val core = CoreConfiguration()

    inner class CoreConfiguration : Configuration("core", this)
}

/**
 * Единожды создает инстанс [ProjectConfiguration] после чего возвращает его кешированное значение.
 */
val Project.projectConfiguration: ProjectConfiguration
    get() = rootProject.extensions.findByType()
        ?: rootProject.extensions.create(
            ProjectConfiguration::class.java.simpleName,
            PropertyProvider { project.findProperty(it)?.toString() }
        )
