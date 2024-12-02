package ru.vs.control.feature.auth.client.domain

/**
 * Результат авторизации на сервере.
 *
 * @property Success успешная авторизация
 * @property IncorrectLoginOrPassword ошибка при проверке логина / пароля
 * @property NetworkError сетевая ошибка
 */
sealed interface ServerAuthResult {
    data class Success(val accessToken: String) : ServerAuthResult
    data object IncorrectLoginOrPassword : ServerAuthResult
    data class NetworkError(val error: Exception) : ServerAuthResult
}
