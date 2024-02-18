package ru.vs.control.feature.initialization.domain

import kotlinx.atomicfu.atomic
import org.kodein.di.DirectDI

class InitializationInteractorImpl(
    private val notInitializedDi: DirectDI
) : InitializationInteractor {
    private val isInitialized = atomic(false)

    override suspend fun init(): DirectDI {
        check(isInitialized.compareAndSet(expect = false, update = true)) {
            "init can be called only once"
        }
        return notInitializedDi
    }
}
