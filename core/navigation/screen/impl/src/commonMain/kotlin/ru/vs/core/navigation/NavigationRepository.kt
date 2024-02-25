package ru.vs.core.navigation

import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey

internal interface NavigationRepository {
    val screenFactories: Map<ScreenKey<out ScreenParams>, ScreenFactory<out ScreenParams, out Screen>>
    val defaultScreenParams: Map<ScreenKey<out ScreenParams>, ScreenParams>
    val navigationHosts: Map<ScreenKey<out ScreenParams>, NavigationHost>
    val endpoints: Map<NavigationHost, Set<ScreenKey<ScreenParams>>>
}

/**
 * Регистрирует все компоненты навигации.
 * @param registrars множество регистраторов экранов.
 */
internal class NavigationRepositoryImpl(
    registrars: Set<NavigationRegistrar>,
) : NavigationRepository {
    override val screenFactories =
        mutableMapOf<ScreenKey<out ScreenParams>, ScreenFactory<out ScreenParams, out Screen>>()
    override val defaultScreenParams = mutableMapOf<ScreenKey<out ScreenParams>, ScreenParams>()
    override val navigationHosts = mutableMapOf<ScreenKey<out ScreenParams>, NavigationHost>()
    override val endpoints = mutableMapOf<NavigationHost, MutableSet<ScreenKey<ScreenParams>>>()

    /**
     * Состояние финализации [NavigationRegistry]. После создания [NavigationRepositoryImpl] добавлять новые элементы
     * нельзя.
     */
    private var isFinalized = false

    private val registry = NavigationRegistryImpl()

    init {
        NavigationLogger.d {
            val registrarsString = registrars.joinToString(
                prefix = "[",
                postfix = "]",
                separator = ",\n",
            ) { it::class.qualifiedName ?: "NO_NAME" }
            "Initializing NavigationRegistry, registrars:\n$registrarsString"
        }
        registrars.forEach { registry.register(it) }
        isFinalized = true
    }

    private inner class NavigationRegistryImpl : NavigationRegistry {
        fun register(registrar: NavigationRegistrar) {
            with(registrar) { register() }
        }

        override fun <P : ScreenParams, S : Screen> registerScreenFactory(
            screenKey: ScreenKey<P>,
            factory: ScreenFactory<P, S>
        ) {
            checkFinalization()
            val oldFactory = screenFactories.put(screenKey, factory)
            check(oldFactory == null) { "Double registration for screenKey=$screenKey" }
        }

        override fun <P : ScreenParams> registerDefaultScreenParams(screenParams: P) {
            checkFinalization()
            val oldDefaultScreenParams = defaultScreenParams.put(ScreenKey(screenParams::class), screenParams)
            check(oldDefaultScreenParams == null) { "Double registration for screenParams = $screenParams" }
        }

        override fun registerNavigationHost(screenKey: ScreenKey<ScreenParams>, navigationHost: NavigationHost) {
            checkFinalization()
            val oldHost = navigationHosts.put(screenKey, navigationHost)
            check(oldHost == null) { "Double registration for screenKey=$screenKey, navigationHost=$navigationHost" }
        }

        override fun registerScreenNavigation(navigationHost: NavigationHost, screenKey: ScreenKey<ScreenParams>) {
            checkFinalization()
            val navigationHostEndpoints = endpoints.getOrPut(navigationHost) { mutableSetOf() }
            val isAdded = navigationHostEndpoints.add(screenKey)
            check(isAdded) { "Double screen registration for navigationHost=$navigationHost, screenKey=$screenKey" }
        }

        private fun checkFinalization() = check(!isFinalized) {
            "NavigationRegistry already finalized. Use NavigationRegistrar to navigation registration"
        }
    }
}
