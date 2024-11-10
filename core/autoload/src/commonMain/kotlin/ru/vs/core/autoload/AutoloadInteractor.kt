package ru.vs.core.autoload

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

interface AutoloadInteractor {
    /**
     * Запускает все [AutoloadService].
     * Возвращает управление только после завершения всех дочерних сервисов.
     */
    suspend fun loadAll()
}

internal class AutoLoadInteractorImpl(
    private val autoloadServices: Set<AutoloadService>,
) : AutoloadInteractor {
    override suspend fun loadAll() = coroutineScope {
        autoloadServices.forEach { service ->
            launch { service.run() }
        }
    }
}
