package ru.vs.control.feature.embeddedServer.domain

/**
 * Содержит информацию о возможности запуска встроенного сервера на текущей платформе.
 */
interface EmbeddedServerSupportInteractor {
    /**
     * Поддерживает ли текущая платформа встроенный сервер.
     */
    fun isSupportedOnCurrentPlatform(): Boolean
}
