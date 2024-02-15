package ru.vs.control.feature.appInfo.domain

/**
 * Предоставляет базовую информацию о приложении.
 */
interface AppInfoInteractor {
    /**
     * Основное имя приложения (не локализированная строка).
     */
    val appName: String
}
