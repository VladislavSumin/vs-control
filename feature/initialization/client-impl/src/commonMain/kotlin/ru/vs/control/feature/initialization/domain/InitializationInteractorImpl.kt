package ru.vs.control.feature.initialization.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.direct

class InitializationInteractorImpl(
    private val notInitializedDi: DirectDI
) : InitializationInteractor {
    private val initLock = Mutex()
    private var initializedDi: DirectDI? = null

    override suspend fun init(): DirectDI = initLock.withLock {
        var di = initializedDi
        if (di == null) {
            di = withContext(Dispatchers.Default) {
                // Тестовая задержка для имитации долгой загрузки
                @Suppress("MagicNumber")
                delay(3000)

                DI {
                    extend(notInitializedDi)
                }.direct
            }
            initializedDi = di
        }
        di
    }
}
