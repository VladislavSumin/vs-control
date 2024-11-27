package ru.vs.control.feature.initialization.client.domain

import org.kodein.di.DirectDI

/**
 * Отвечает за полную инициализацию приложения.
 */
interface InitializationInteractor {
    /**
     * Инициализирует приложение. При повторном вызове возвращает результат из кеша.
     * @return DI граф инициализированного приложения.
     */
    suspend fun init(): DirectDI
}
