package ru.vs.core.navigation

import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

/**
 * Ключ экрана.
 * @param P тип параметров экрана.
 * @param key сырой тип ключа.
 */
@JvmInline
value class ScreenKey<P : ScreenParams>(val key: KClass<P>)
