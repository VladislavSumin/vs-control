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

    inner class CoreConfiguration : Configuration("core", this) {
        /**
         * Версия jvm используемая для сборки проекта
         */
        val jvmVersion = property("jvmVersion", "17")

        val android = Android()

        /**
         * Настройки android плагина.
         */
        @Suppress("MagicNumber") // В данном случае значение цифр понятно без пояснения.
        inner class Android : Configuration("android", this) {
            val minSdk = property("minSdk", 24)
            val targetSdk = property("targetSdk", 34)
            val compileSdk = property("compileSdk", 34)
        }
    }
}

/**
 * Единожды создает инстанс [ProjectConfiguration] после чего возвращает его кешированное значение.
 */
val Project.projectConfiguration: ProjectConfiguration
    get() = rootProject.extensions.findByType()
        ?: rootProject.extensions.create(
            ProjectConfiguration::class.java.simpleName,
            PropertyProvider { project.findProperty(it)?.toString() },
        )
