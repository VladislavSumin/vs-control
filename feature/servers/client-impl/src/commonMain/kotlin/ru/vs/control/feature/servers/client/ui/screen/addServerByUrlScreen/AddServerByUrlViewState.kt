package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import ru.vs.control.feature.serverInfo.client.domain.ServerInfo

internal sealed interface AddServerByUrlViewState {
    val url: String

    /**
     * Состояние ввода url.
     */
    data class EnterUrl(
        override val url: String,
        val urlError: UrlError,
        val isCheckConnectionButtonEnabled: Boolean,
    ) : AddServerByUrlViewState

    /**
     * Состояние первичной проверки соединения после ввода url.
     */
    data class CheckingConnection(override val url: String) : AddServerByUrlViewState

    /**
     * Состояние ошибки при любых проблемах с подключением к серверу.
     */
    data class ConnectionError(override val url: String, val error: String) : AddServerByUrlViewState

    sealed interface ServerInfoProvider : AddServerByUrlViewState {
        val serverInfo: ServerInfo
    }

    /**
     * Состояние ввода логина и пароля после первичной проверки соединения.
     */
    data class EnterCredentials(
        override val url: String,
        override val serverInfo: ServerInfo,
        val login: String,
        val password: String,
    ) : AddServerByUrlViewState, ServerInfoProvider

    /**
     * Состояние отправки запроса на получение токена по учетным данным.
     */
    data class CheckingCredentials(
        override val url: String,
        override val serverInfo: ServerInfo,
        val login: String,
        val password: String,
    ) : AddServerByUrlViewState, ServerInfoProvider

    sealed interface UrlError {
        data object None : UrlError
        data object UrlWithPathOrQuery : UrlError
        data object MalformedUrl : UrlError

        fun isError() = when (this) {
            None -> false
            UrlWithPathOrQuery,
            MalformedUrl,
            -> true
        }
    }
}
