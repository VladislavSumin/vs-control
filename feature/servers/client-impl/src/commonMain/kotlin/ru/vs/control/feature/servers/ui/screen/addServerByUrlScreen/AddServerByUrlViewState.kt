package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen

internal sealed interface AddServerByUrlViewState {
    /**
     * Состояние ввода url.
     */
    data class EnterUrl(val url: String) : AddServerByUrlViewState

    /**
     * Состояние первичной проверки соединения после ввода url.
     */
    data class CheckConnection(val url: String) : AddServerByUrlViewState

    /**
     * Состояние предупреждения когда сервер использует самоподписанные сертификаты.
     */
    data class SslError(val url: String) : AddServerByUrlViewState

    /**
     * Состояние ввода логина и пароля после первичной проверки соединения.
     */
    data class EnterCredentials(val url: String) : AddServerByUrlViewState
}
