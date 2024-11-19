package ru.vs.core.properties

import com.russhwolf.settings.coroutines.toFlowSettings
import ru.vs.core.coroutines.DispatchersProvider

internal class PropertiesServiceImpl(
    private val flowSettingsFactory: FlowSettingsFactory,
    private val dispatchersProvider: DispatchersProvider,
) : PropertiesService {
    override fun getProperties(propertiesKey: PropertiesKey): Properties {
        val settings = flowSettingsFactory.createSettings(propertiesKey.key)
        return PropertiesImpl(settings.toFlowSettings(dispatchersProvider.default))
    }
}
