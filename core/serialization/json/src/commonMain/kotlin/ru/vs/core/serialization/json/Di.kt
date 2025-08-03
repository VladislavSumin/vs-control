package ru.vs.core.serialization.json

import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.core.serialization.core.coreSerializationCore

fun Modules.coreSerializationJson() = DI.Module("core-serialization-json") {
    importOnce(Modules.coreSerializationCore())

    bindSingleton<JsonFactory> { JsonFactoryImpl(i()) }

    /**
     * Default [Json] инстанс кладется в граф для возможности получить к нему доступ из любой точки приложения.
     */
    bindSingleton<Json> { i<JsonFactory>().createDefault() }
}
