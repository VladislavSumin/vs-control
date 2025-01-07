package ru.vs.core.serialization.protobuf

import org.kodein.di.DI
import ru.vs.core.di.Modules
import ru.vs.core.serialization.core.coreSerializationCore

fun Modules.coreSerializationProtobuf() = DI.Module("core-serialization-protobuf") {
    importOnce(Modules.coreSerializationCore())
}
