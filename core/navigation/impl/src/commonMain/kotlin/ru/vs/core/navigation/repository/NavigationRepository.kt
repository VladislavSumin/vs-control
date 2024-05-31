package ru.vs.core.navigation.repository

import kotlinx.serialization.KSerializer
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.NavigationLogger
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
import ru.vs.core.navigation.screen.DefaultScreenKey
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.tree.NavigationTree
import ru.vs.core.utils.joinToStingFormatted

/**
 * Репозиторий навигации, используется для построения [NavigationTree].
 */
internal interface NavigationRepository {
    /**
     * Список всех зарегистрированных экранов.
     */
    val screens: Map<DefaultScreenKey, DefaultScreenRegistration>

    /**
     * Список экранов которые могут открываться внутри определенных [NavigationHost].
     */
    val endpoints: Map<NavigationHost, Set<DefaultScreenKey>>

    /**
     * Множество [KSerializer] для сериализации [ScreenParams].
     */
    val serializers: Map<DefaultScreenKey, KSerializer<out ScreenParams>>
}

/**
 * Регистрирует все компоненты навигации.
 *
 * @param registrars множество регистраторов экранов.
 */
internal class NavigationRepositoryImpl(
    registrars: Set<NavigationRegistrar>,
) : NavigationRepository {
    override val screens = mutableMapOf<DefaultScreenKey, DefaultScreenRegistration>()
    override val endpoints = mutableMapOf<NavigationHost, MutableSet<DefaultScreenKey>>()
    override val serializers = mutableMapOf<DefaultScreenKey, KSerializer<out ScreenParams>>()

    /**
     * Состояние финализации [NavigationRegistry]. После создания [NavigationRepositoryImpl] добавлять новые элементы
     * нельзя.
     */
    private var isFinalized = false

    private val registry = NavigationRegistryImpl()

    init {
        NavigationLogger.d {
            val registrarsString = registrars.joinToStingFormatted { it.nameForLogs }
            "Initializing NavigationRegistry, registrars:\n$registrarsString"
        }
        registrars.forEach { registry.register(it) }
        isFinalized = true
    }

    private inner class NavigationRegistryImpl : NavigationRegistry {
        fun register(registrar: NavigationRegistrar) {
            with(registrar) { register() }
        }

        override fun <P : ScreenParams, S : Screen> registerScreen(
            key: ScreenKey<P>,
            factory: ScreenFactory<P, S>,
            paramsSerializer: KSerializer<P>,
            defaultParams: P?,
            navigationHosts: List<NavigationHost>,
        ) {
            serializers[key] = paramsSerializer
            val screenRegistration = ScreenRegistration(factory, defaultParams, navigationHosts)
            val oldRegistration = screens.put(key, screenRegistration)
            check(oldRegistration == null) { "Double registration for key=$key" }
        }

        override fun <P : ScreenParams> registerScreenNavigation(
            navigationHost: NavigationHost,
            screenKey: ScreenKey<P>,
        ) {
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
