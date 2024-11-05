package ru.vs.control.feature.embeddedServer.domain

interface EmbeddedServerSupportInteractor {
    /**
     * Поддерживает ли текущая платформа встроенный сервер.
     */
    fun isSupportedOnCurrentPlatform(): Boolean
}
