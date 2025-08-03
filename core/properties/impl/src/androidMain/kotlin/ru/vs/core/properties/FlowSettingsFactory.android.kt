package ru.vs.core.properties

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.kodein.di.DirectDI
import ru.vladislavsumin.core.di.i

private class FlowSettingsFactoryImpl(context: Context) : FlowSettingsFactory {
    private val factory = SharedPreferencesSettings.Factory(context)

    override fun createSettings(name: String): ObservableSettings {
        return factory.create(name)
    }
}

internal actual fun DirectDI.createSettingsFactory(): FlowSettingsFactory {
    return FlowSettingsFactoryImpl(i())
}
