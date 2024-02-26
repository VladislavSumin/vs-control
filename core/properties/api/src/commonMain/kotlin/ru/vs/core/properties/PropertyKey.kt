package ru.vs.core.properties

import kotlin.jvm.JvmInline

/**
 * Ключ для доступа к [Property].
 *
 * @param key сырой идентификатор ключа.
 */
@JvmInline
value class PropertyKey(val key: String)
