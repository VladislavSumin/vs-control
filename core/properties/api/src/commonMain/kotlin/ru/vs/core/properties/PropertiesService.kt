package ru.vs.core.properties

/**
 * Позволяет получать доступ к хранилищам [Properties].
 */
interface PropertiesService {
    /**
     * Возвращает хранилище [Properties] соответствующее переданному [PropertiesKey].
     */
    fun getProperties(propertiesKey: PropertiesKey): Properties
}
