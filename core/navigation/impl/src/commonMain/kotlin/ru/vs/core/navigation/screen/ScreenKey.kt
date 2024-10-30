package ru.vs.core.navigation.screen

import ru.vs.core.navigation.ScreenParams
import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

/**
 * Ключ экрана.
 * @param P тип параметров экрана.
 * @param key сырой тип ключа.
 */
@JvmInline
value class ScreenKey<P : ScreenParams> internal constructor(val key: KClass<P>)

fun <T : ScreenParams> T.asKey(): ScreenKey<T> = ScreenKey(this::class) as ScreenKey<T>

internal fun ScreenParams.asErasedKey(): ScreenKey<ScreenParams> = ScreenKey(this::class) as ScreenKey<ScreenParams>
