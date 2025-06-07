package ru.vs.core.properties

import kotlin.jvm.JvmInline

// TODO сделать абстрактным классом
/**
 * Ключ для доступа к [Properties].
 *
 * @param key сырой идентификатор ключа.
 */
@JvmInline
value class PropertiesKey(val key: String)
