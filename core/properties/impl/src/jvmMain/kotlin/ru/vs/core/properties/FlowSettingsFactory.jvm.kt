package ru.vs.core.properties

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import org.kodein.di.DirectDI

private class FlowSettingsFactoryImpl : FlowSettingsFactory {
    private val factory = PreferencesSettings.Factory()

    override fun createSettings(name: String): ObservableSettings {
        return factory.create(name)
    }
}

internal actual fun DirectDI.createSettingsFactory(): FlowSettingsFactory {
    return FlowSettingsFactoryImpl()
}
