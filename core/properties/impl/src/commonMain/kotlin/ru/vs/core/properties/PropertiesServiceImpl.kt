package ru.vs.core.properties

internal class PropertiesServiceImpl(
    private val dataStorePreferencesFactory: DataStorePreferencesFactory,
) : PropertiesService {
    override fun getProperties(propertiesKey: PropertiesKey): Properties {
        // TODO нужно сюда скоуп пробрасывать. И всякие штуки для работы с ошибками
        val dataStore = dataStorePreferencesFactory.create(propertiesKey.key)
        return PropertiesImpl(dataStore)
    }
}
