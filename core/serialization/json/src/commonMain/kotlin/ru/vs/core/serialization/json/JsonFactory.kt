package ru.vs.core.serialization.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import ru.vs.core.serialization.core.SerializersModulesRepository

interface JsonFactory {
    /**
     * Создает default [Json] инстанс. Добавляет в него [SerializersModule] объявленные в DI графе.
     */
    fun createDefault(): Json
}

internal class JsonFactoryImpl(
    private val serializersModulesRepository: SerializersModulesRepository,
) : JsonFactory {

    override fun createDefault() = Json {
        serializersModule = serializersModulesRepository.serializerModule
    }
}
