package ru.vs.core.navigation.repository

import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory

/**
 * Содержит информацию о регистрации экрана.
 *
 * @param P тип параметров экрана.
 * @param S тип экрана.
 *
 * @param factory фабрика для создания компонента экрана.
 * @param defaultParams параметры экрана по умолчанию.
 * @param navigationHosts список [NavigationHost] которые открываются с этого экрана.
 * @param nameForLogs название экрана для логирования. Нужно так как мы не можем использовать class.qualifiedName в js.
 */
internal data class ScreenRegistration<P : ScreenParams, S : Screen>(
    val factory: ScreenFactory<P, S>,
    val defaultParams: P?,
    val navigationHosts: List<NavigationHost>,
    val nameForLogs: String,
)

internal typealias DefaultScreenRegistration = ScreenRegistration<out ScreenParams, out Screen>
