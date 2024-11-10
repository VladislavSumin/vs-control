package ru.vs.control.feature.initialization.domain

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
            di = initUnsafe()
            initializedDi = di
        }
        di
    }

    private suspend fun initUnsafe(): DirectDI = withContext(Dispatchers.Default) {
        val di = DI {
            extend(notInitializedDi)
            with(initializedDependenciesBuilder) { build() }
        }.direct

        // Запускаем все autostart сервисы.
        val appScope = di.instance<CoroutineScope>()
        val autoloadInteractor = di.instance<AutoloadInteractor>()
        appScope.launch(Dispatchers.Default) { autoloadInteractor.loadAll() }

        di
    }
}
