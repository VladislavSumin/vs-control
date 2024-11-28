package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

internal sealed interface AddServerByUrlViewState {
    val url: String

    /**
     * Состояние ввода url.
     */
    data class EnterUrl(override val url: String) : AddServerByUrlViewState

    /**
     * Состояние первичной проверки соединения после ввода url.
     */
    data class CheckingConnection(override val url: String) : AddServerByUrlViewState

    /**
     * Состояние предупреждения когда сервер использует самоподписанные сертификаты.
     */
    data class SslError(override val url: String) : AddServerByUrlViewState

    /**
     * Состояние ввода логина и пароля после первичной проверки соединения.
     */
    data class EnterCredentials(override val url: String) : AddServerByUrlViewState
}
