package ru.vs.core.properties

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.coreProperties() = DI.Module("core-properties") {
    bindSingleton<PropertiesService> { PropertiesServiceImpl(createSettingsFactory(), i()) }
}
