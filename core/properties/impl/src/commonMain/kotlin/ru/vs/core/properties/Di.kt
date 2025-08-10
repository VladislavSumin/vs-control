package ru.vs.core.properties

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vladislavsumin.core.di.Modules
import ru.vladislavsumin.core.di.i

fun Modules.coreProperties() = DI.Module("core-properties") {
    bindSingleton<PropertiesService> {
        val dataStorePreferencesFactory = DataStorePreferencesFactoryImpl(i(), i())
        PropertiesServiceImpl(dataStorePreferencesFactory)
    }
}
