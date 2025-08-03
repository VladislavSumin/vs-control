package ru.vs.core.serialization.protobuf

import kotlinx.serialization.protobuf.ProtoBuf
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i
import ru.vs.core.serialization.core.coreSerializationCore

fun Modules.coreSerializationProtobuf() = DI.Module("core-serialization-protobuf") {
    importOnce(Modules.coreSerializationCore())

    bindSingleton<ProtobufFactory> { ProtobufFactoryImpl(i()) }

    /**
     * Default [ProtoBuf] инстанс кладется в граф для возможности получить к нему доступ из любой точки приложения.
     */
    bindSingleton<ProtoBuf> { i<ProtobufFactory>().createDefault() }
}
