package ru.vs.core.serialization.core

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

interface SerializersModulesRepository {
    /**
     * Возвращает общий [SerializersModule] для всех модулей зарегистрированных через di.
     */
    val serializerModule: SerializersModule
}

internal class SerializersModulesRepositoryImpl(
    serializersModulesSet: Set<SerializersModule>,
) : SerializersModulesRepository {
    override val serializerModule: SerializersModule by lazy {
        /**
         * Конкатенирует все [serializersModulesSet] в один [SerializersModule].
         */
        serializersModulesSet.fold(null as SerializersModule?) { m1, m2 ->
            m1?.plus(m2) ?: m2
        } ?: SerializersModule { }
    }
}
