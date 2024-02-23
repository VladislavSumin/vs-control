package ru.vs.core.navigation

import ru.vs.navigation.ScreenParams
import kotlin.reflect.KClass

/**
 * Навигатор, который может производить навигацию в пределах одного хоста навигации.
 */
internal interface HostNavigator {
    fun open(params: ScreenParams)
    fun close(params: ScreenParams)
    fun close(screenKey: KClass<ScreenParams>)
}
