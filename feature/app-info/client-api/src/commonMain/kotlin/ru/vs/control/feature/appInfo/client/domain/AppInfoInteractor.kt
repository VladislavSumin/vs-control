package ru.vs.control.feature.appInfo.client.domain

/**
 * Предоставляет базовую информацию о приложении.
 */
interface AppInfoInteractor {
    /**
     * Основное имя приложения (не локализированная строка).
     */
    val appName: String
}
