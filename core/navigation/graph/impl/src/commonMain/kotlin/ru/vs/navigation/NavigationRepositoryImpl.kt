package ru.vs.navigation

import kotlin.reflect.KClass

/**
 * Регистрирует все компоненты навигации.
 * @param registrars множество регистраторов экранов.
 */
internal class NavigationRepositoryImpl(
    registrars: Set<NavigationRegistrar>,
) : NavigationRepository {
    override val screenFactories = mutableMapOf<KClass<out ScreenParams>, ScreenFactory<out ScreenParams, out Screen>>()
    override val navigationHosts = mutableMapOf<KClass<out ScreenParams>, NavigationHost>()
    override val endpoints = mutableMapOf<NavigationHost, MutableSet<KClass<ScreenParams>>>()

    /**
     * Состояние финализации [NavigationRegistry]. После создания [NavigationRepositoryImpl] добавлять новые элементы
     * нельзя.
     */
    private var isFinalized = false

    private val registry: NavigationRegistry = NavigationRegistryImpl()

    init {
        NavigationLogger.d {
            val registrarsString = registrars.joinToString(
                prefix = "[",
                postfix = "]",
                separator = ",\n",
            ) { it::class.simpleName ?: "NO_NAME" }
            "Initializing NavigationRegistry, registrars:\n$registrarsString"
        }
        registrars.forEach { it.register(registry) }
        isFinalized = true
    }

    private inner class NavigationRegistryImpl : NavigationRegistry {
        override fun <P : ScreenParams, S : Screen> registerScreenFactory(
            screenKey: KClass<P>,
            factory: ScreenFactory<P, S>
        ) {
            checkFinalization()
            val oldFactory = screenFactories.put(screenKey, factory)
            check(oldFactory == null) { "Double registration for screenKey=$screenKey" }
        }

        override fun registerNavigationHost(screenKey: KClass<ScreenParams>, navigationHost: NavigationHost) {
            checkFinalization()
            val oldHost = navigationHosts.put(screenKey, navigationHost)
            check(oldHost == null) { "Double registration for screenKey=$screenKey, navigationHost=$navigationHost" }
        }

        override fun registerScreenNavigation(navigationHost: NavigationHost, screenKey: KClass<ScreenParams>) {
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