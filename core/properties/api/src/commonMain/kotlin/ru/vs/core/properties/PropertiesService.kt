package ru.vs.core.properties

import kotlin.reflect.KProperty

/**
 * Позволяет получать доступ к хранилищам [Properties].
 */
interface PropertiesService {
    /**
     * Возвращает хранилище [Properties] соответствующее переданному [PropertiesKey].
     */
    fun getProperties(propertiesKey: PropertiesKey): Properties

    /**
     * Делегат для доступа к [Properties] с использованием в качестве ключа конкатенации полного имени класса и
     * названия поля.
     *
     * **Обратите внимание!** При изменении пакета, названия класса или названия поля изменится и ключ.
     */
    operator fun getValue(thisRef: Any, property: KProperty<*>): Properties {
        // TODO пока не поддерживается к котлине для js.
        error("NOT_SUPPORTED")

        // val className = thisRef::class.qualifiedName
        // check(className != null) { "Properties delegate not allowed for unnamed classes" }
        // val name = "${className.replace(".", "_")}_${property.name}"
        // return getProperties(PropertiesKey(name))
    }
}
