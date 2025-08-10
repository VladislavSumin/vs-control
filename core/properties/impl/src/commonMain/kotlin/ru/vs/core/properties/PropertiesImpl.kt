package ru.vs.core.properties

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PropertiesImpl(
    private val dataStore: DataStore<Preferences>,
) : Properties {

    @Suppress("UNCHECKED_CAST")
    override fun <T> getProperty(
        propertyKey: PropertyKey<T>,
        defaultValue: T,
    ): Property<T> {
        return when (propertyKey.type) {
            PropertyType.Boolean -> {
                val dataStoreKey = booleanPreferencesKey(propertyKey.key)
                val flow: Flow<Boolean> = dataStore.data.map { it[dataStoreKey] ?: defaultValue as Boolean }
                PropertyImpl(flow) { value -> dataStore.edit { it[dataStoreKey] = value } } as Property<T>
            }
        }
    }
}
