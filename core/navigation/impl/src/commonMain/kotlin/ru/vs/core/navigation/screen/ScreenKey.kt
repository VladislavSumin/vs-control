package ru.vs.core.navigation.screen

import ru.vladislavsumin.core.navigation.ScreenParams
import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

/**
 * Ключ экрана.
 * @param P тип параметров экрана.
 * @param key сырой тип ключа.
 */
@JvmInline
@PublishedApi
internal value class ScreenKey<P : ScreenParams>(val key: KClass<P>)

internal fun <T : ScreenParams> T.asKey(): ScreenKey<T> = ScreenKey(this::class) as ScreenKey<T>

internal fun ScreenParams.asErasedKey(): ScreenKey<ScreenParams> = ScreenKey(this::class) as ScreenKey<ScreenParams>
