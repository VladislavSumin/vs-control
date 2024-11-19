package ru.vs.core.properties

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@OptIn(ExperimentalSettingsApi::class)
internal class PropertiesImpl(
    private val settings: FlowSettings,
) : Properties() {

    @Suppress("UNCHECKED_CAST")
    override fun <T> getProperty(
        propertyKey: PropertyKey,
        defaultValue: T,
        type: KType,
    ): Property<T> {
        val key = propertyKey.key
        return when {
            type == BOOLEAN_TYPE -> {
                val flow = settings.getBooleanFlow(key, defaultValue as Boolean)
                PropertyImpl(flow) { settings.putBoolean(key, it) } as Property<T>
            }
            else -> error("Unsupported")
        }
    }

    private companion object {
        private val BOOLEAN_TYPE = typeOf<Boolean>()
    }
}
