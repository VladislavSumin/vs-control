package ru.vs.configuration

import kotlin.reflect.KClass

/**
 * Базовый класс для удобной работы с project properties и создания удобного типизированного dsl.
 * Подробнее о создании dsl с помощью этого класса смотрите в документации.
 *
 * @param basePath префикс проперти относительно которого будут считаться все остальные имена пропертей
 * @param propertyProvider провайдер с помощью которого [Configuration] будет пытаться зарезолвить значения пропертей.
 */
@Suppress("UnnecessaryAbstractClass") // Создавать этот класс напрямую не имеет смысла
abstract class Configuration(
    private val basePath: String,
    private val propertyProvider: PropertyProvider,
) {
    /**
     * Конструктор для создания дочерней секции пропертей относительно родительской.
     * @param relativePath путь, который будет добавлен к пути родителя для получения финального пути этой конфигурации.
     */
    constructor(relativePath: String, parent: Configuration) : this(
        "${parent.basePath}.$relativePath",
        parent.propertyProvider,
    )

    protected inline fun <reified T : Any> property(relativePath: String, defaultValue: T): T =
        propertyOrNull(relativePath, T::class) ?: defaultValue

    protected inline fun <reified T : Any> property(relativePath: String): T =
        propertyOrNull(relativePath, T::class)!!

    protected inline fun <reified T : Any> propertyOrNull(relativePath: String): T? =
        propertyOrNull(relativePath, T::class)

    protected fun <T : Any> propertyOrNull(relativePath: String, kClass: KClass<T>): T? {
        val path = if (basePath.isEmpty()) {
            relativePath
        } else if (relativePath.isEmpty()) {
            basePath
        } else {
            "$basePath.$relativePath"
        }

        val rawProperty = propertyProvider.getProperty(path) ?: return null

        @Suppress("UNCHECKED_CAST")
        return when (kClass) {
            String::class -> rawProperty
            Boolean::class -> rawProperty.toBoolean()
            Integer::class -> rawProperty.toInt()
            else -> Error("Unsupported cast to ${kClass.simpleName}")
        } as T
    }
}

fun interface PropertyProvider {
    /**
     * Предоставляет значение проверти по ее имени.
     *
     * @param name имя проперти.
     * @return значение проперти или null, если проперти не существует.
     */
    fun getProperty(name: String): String?
}
