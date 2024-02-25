package ru.vs.core.navigation

/**
 * Позволяет регистрировать компоненты навигации.
 * Использовать напрямую этот интерфейс нельзя так как его состояние финализируется в процессе инициализации приложения.
 * Для доступа к [NavigationRegistry] воспользуйтесь [NavigationRegistrar].
 */
interface NavigationRegistry {
    /**
     * Регистрирует фабрику компонента экрана.
     *
     * @param P тип параметров экрана.
     * @param S тип экрана.
     * @param screenKey ключ экрана.
     * @param factory фабрика компонента экрана.
     */
    fun <P : ScreenParams, S : Screen> registerScreenFactory(screenKey: ScreenKey<P>, factory: ScreenFactory<P, S>)

    /**
     * Регистрирует параметры для экрана по умолчанию. Если параметры экрана не переданы явно, будут применены эти
     * параметры.
     *
     * @param screenParams параметры экрана.
     */
    fun <P : ScreenParams> registerDefaultScreenParams(screenParams: P)

    /**
     * Регистрирует [navigationHost] в экране с ключом [screenKey].
     *
     * @param screenKey ключ экрана.
     * @param navigationHost хост навигации.
     */
    fun registerNavigationHost(screenKey: ScreenKey<ScreenParams>, navigationHost: NavigationHost)

    /**
     * Регистрирует экран с ключом [screenKey] в [navigationHost], это означает что данный экран сможет быть открыть в
     * переданном хосте навигации.
     *
     * @param screenKey ключ экрана.
     * @param navigationHost хост навигации.
     */
    fun registerScreenNavigation(navigationHost: NavigationHost, screenKey: ScreenKey<ScreenParams>)
}
