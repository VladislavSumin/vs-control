package ru.vs.navigation

internal class NavigationGraphImpl(
    registrars: Set<NavigationRegistrar>,
) : NavigationGraph {
    private val repository: NavigationRepository = NavigationRepositoryImpl(registrars)
    private val rootScreen = findRootScreen(repository)

    override fun <P : ScreenParams> findFactory(screenKey: ScreenKey<P>): ScreenFactory<P, out Screen>? {
        // Успешность каста гарантирована контрактом репозитория.
        @Suppress("UNCHECKED_CAST")
        return repository.screenFactories[screenKey] as ScreenFactory<P, out Screen>?
    }

    override fun getRootScreenParams(): ScreenParams {
        return repository.defaultScreenParams[rootScreen] ?: error("Root screen must have default params")
    }

    /**
     * Ищет root screen, этим экраном является такой экран который невозможно открыть из другой точки графа.
     */
    private fun findRootScreen(repository: NavigationRepository): ScreenKey<out ScreenParams> {
        // TODO супер временное решение, пока просто берем первый экран из репозитория
        return repository.screenFactories.keys.first()
    }
}
