package ru.vs.control.feature.initialization.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.core.autoload.AutoloadInteractor
import ru.vs.core.coroutines.DispatchersProvider

class InitializationInteractorImpl(
    private val notInitializedDi: DirectDI,
    private val initializedDependenciesBuilder: InitializedDependenciesBuilder,
) : InitializationInteractor {
    /**
     * [Mutex] для защиты от двойной инициализации.
     */
    private val initLock = Mutex()

    /**
     * Граф инициализированного приложения.
     */
    private var initializedDi: DirectDI? = null

    override suspend fun init(): DirectDI = initLock.withLock {
        var di = initializedDi
        if (di == null) {
            val dispatchersProvider = notInitializedDi.instance<DispatchersProvider>()
            di = initUnsafe(dispatchersProvider.default)
            initializedDi = di
        }
        di
    }

    private suspend fun initUnsafe(dispatcher: CoroutineDispatcher): DirectDI = withContext(dispatcher) {
        val di = DI {
            extend(notInitializedDi)
            with(initializedDependenciesBuilder) { build() }
        }.direct

        // Запускаем все autostart сервисы.
        val appScope = di.instance<CoroutineScope>()
        val autoloadInteractor = di.instance<AutoloadInteractor>()
        appScope.launch(dispatcher) { autoloadInteractor.loadAll() }

        di
    }
}
