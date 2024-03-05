package ru.vs.core.serialization.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

interface JsonFactory {
    /**
     * Создает default [Json] инстанс. Добавляет в него [SerializersModule] объявленные в DI графе.
     */
    fun createDefault(): Json
}

/**
 * @param serializersModulesSet - множество глобальных [SerializersModule], используется для создания Json инстанса
 * по умолчанию через [createDefault].
 */
internal class JsonFactoryImpl(
    private val serializersModulesSet: Set<SerializersModule>,
) : JsonFactory {

    private val mergedModule: SerializersModule by lazy {
        /**
         * Конкатенирует все [serializersModulesSet] в один [SerializersModule].
         */
        serializersModulesSet.fold(null as SerializersModule?) { m1, m2 ->
            m1?.plus(m2) ?: m2
        } ?: SerializersModule { }
    }

    override fun createDefault() = Json {
        serializersModule = mergedModule
    }
}
