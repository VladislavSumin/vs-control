package ru.vs.core.serialization.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreSerializationJson() = DI.Module("core-serialization-json") {
    /**
     * Вы можете добавить [SerializersModule] в это множество, они будут переданы в [JsonFactory] и использованы для
     * создания default [Json] инстанса.
     */
    bindSet<SerializersModule>()

    bindSingleton<JsonFactory> { JsonFactoryImpl(i()) }

    /**
     * Default [Json] инстанс кладется в граф для возможности получить к нему доступ из любой точки приложения.
     */
    bindSingleton<Json> { i<JsonFactory>().createDefault() }
}
