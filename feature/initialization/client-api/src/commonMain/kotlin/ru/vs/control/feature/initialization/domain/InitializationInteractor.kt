package ru.vs.control.feature.initialization.domain

import org.kodein.di.DirectDI

/**
 * Отвечает за полную инициализацию приложения.
 */
interface InitializationInteractor {
    /**
     * Инициализирует приложение. При повторном вызове выкинет исключение.
     * @return DI граф инициализированного приложения.
     */
    suspend fun init(): DirectDI
}
