package ru.vs.core.autoload

/**
 * Интерфейс для сервисов которым нужен автоматический старт на старте приложения.
 */
interface AutoloadService {
    suspend fun run()
}
