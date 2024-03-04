package ru.vs.core.navigation.registration

import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey

/**
 * Позволяет регистрировать компоненты навигации.
 * Использовать напрямую этот интерфейс нельзя так как его состояние финализируется в процессе инициализации приложения.
 * Для доступа к [NavigationRegistry] воспользуйтесь [NavigationRegistrar].
 */
interface NavigationRegistry {

    /**
     * Регистрирует экран.
     *
     * @param P тип параметров экрана.
     * @param S тип экрана.
     * @param key ключ экрана.
     * @param factory фабрика компонента экрана.
     * @param defaultParams параметры экрана по умолчанию.
     * @param navigationHosts хосты навигации расположенные на этом экране
     */
    fun <P : ScreenParams, S : Screen> registerScreen(
        key: ScreenKey<P>,
        factory: ScreenFactory<P, S>,
        defaultParams: P? = null,
        navigationHosts: List<NavigationHost> = emptyList(),
    )

    /**
     * Регистрирует экран с ключом [screenKey] в [navigationHost], это означает что данный экран сможет быть открыть в
     * переданном хосте навигации.
     *
     * @param screenKey ключ экрана.
     * @param navigationHost хост навигации.
     */
    fun <P : ScreenParams> registerScreenNavigation(navigationHost: NavigationHost, screenKey: ScreenKey<P>)
}
