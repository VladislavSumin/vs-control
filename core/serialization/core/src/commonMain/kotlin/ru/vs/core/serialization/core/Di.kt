package ru.vs.core.serialization.core

import kotlinx.serialization.modules.SerializersModule
import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i

fun Modules.coreSerializationCore() = DI.Module("core-serialization-core") {
    /**
     * Вы можете добавить [SerializersModule] в это множество, они будут переданы в [SerializersModulesRepository]
     * и использованы для создания default сериализаторов в будущем.
     */
    bindSet<SerializersModule>()

    bindSingleton<SerializersModulesRepository> { SerializersModulesRepositoryImpl(i()) }
}
