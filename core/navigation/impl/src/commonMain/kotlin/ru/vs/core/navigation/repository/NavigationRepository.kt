package ru.vs.core.navigation.repository

import kotlinx.serialization.KSerializer
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.NavigationLogger
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.registration.NavigationRegistrar
import ru.vs.core.navigation.registration.NavigationRegistry
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
    val screens: Map<ScreenKey<*>, ScreenRegistration<*, *>>

    /**
     * Множество [KSerializer] для сериализации [ScreenParams].
     */
    val serializers: Map<ScreenKey<*>, KSerializer<out ScreenParams>>
}

/**
 * Регистрирует все компоненты навигации.
 *
 * @param registrars множество регистраторов экранов.
 */
internal class NavigationRepositoryImpl(
    registrars: Set<NavigationRegistrar>,
) : NavigationRepository {
    override val screens = mutableMapOf<ScreenKey<*>, ScreenRegistration<*, *>>()
    override val serializers = mutableMapOf<ScreenKey<*>, KSerializer<out ScreenParams>>()

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
            nameForLogs: String,
            defaultParams: P?,
            opensIn: Set<NavigationHost>,
            navigationHosts: Set<NavigationHost>,
            description: String?,
        ) {
            checkFinalization()

            serializers[key] = paramsSerializer
            val screenRegistration = ScreenRegistration(
                factory = factory,
                defaultParams = defaultParams,
                opensIn = opensIn,
                navigationHosts = navigationHosts,
                nameForLogs = nameForLogs,
                description = description,
            )
            val oldRegistration = screens.put(key, screenRegistration)
            check(oldRegistration == null) { "Double registration for key=$key" }
        }

        private fun checkFinalization() = check(!isFinalized) {
            "NavigationRegistry already finalized. Use NavigationRegistrar to navigation registration"
        }
    }
}
