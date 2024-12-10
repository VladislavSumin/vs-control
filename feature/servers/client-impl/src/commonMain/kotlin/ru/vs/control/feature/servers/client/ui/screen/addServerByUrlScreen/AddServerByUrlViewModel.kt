package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Stable
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import ru.vs.control.feature.auth.client.domain.ServerAuthInteractor
import ru.vs.control.feature.auth.client.domain.ServerAuthResult
import ru.vs.control.feature.serverInfo.client.domain.ServerInfo
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor
import ru.vs.control.feature.servers.client.domain.ServersInteractor
import ru.vs.control.feature.servers.client.repository.Server
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.ktor.client.SafeResponse
import ru.vs.core.logger.api.logger
import ru.vs.core.navigation.viewModel.NavigationViewModel
import kotlin.error

@Stable
@GenerateFactory
internal class AddServerByUrlViewModel(
    private val serverInfoInteractor: ServerInfoInteractor,
    private val serverAuthInteractor: ServerAuthInteractor,
    private val serversInteractor: ServersInteractor,
) : NavigationViewModel() {
    private val logger = logger("AddServerByUrl")

    private val serverUrl = saveableStateFlow(SERVER_URL_KEY, "")
    private val login = saveableStateFlow(LOGIN_KEY, "")

    // Пароль не сохраняем
    private val password = MutableStateFlow("")

    private val internalState = MutableStateFlow<InternalState>(InternalState.EnterUrl)

    val state: StateFlow<AddServerByUrlViewState> =
        combine(
            internalState,
            serverUrl,
            login,
            password,
        ) { state, url, login, password ->
            when (state) {
                is InternalState.EnterUrl -> {
                    val urlResult = createUrl()
                    AddServerByUrlViewState.EnterUrl(
                        url = url,
                        urlError = urlResult.toUi(),
                        isCheckConnectionButtonEnabled = urlResult.isValid(),
                    )
                }

                is InternalState.CheckConnection -> AddServerByUrlViewState.CheckingConnection(url)
                is InternalState.ConnectionError -> {
                    val message = state.e.message ?: state.e.stackTraceToString().lines().firstOrNull() ?: ""
                    AddServerByUrlViewState.ConnectionError(url, message)
                }

                is InternalState.EnterCredentials -> {
                    AddServerByUrlViewState.EnterCredentials(
                        url = url,
                        serverInfo = state.serverInfo,
                        login = login,
                        password = password,
                    )
                }

                is InternalState.CheckCredentials -> {
                    AddServerByUrlViewState.CheckingCredentials(
                        url = url,
                        serverInfo = state.serverInfo,
                        login = login,
                        password = password,
                    )
                }

                is InternalState.LoginError -> {
                    AddServerByUrlViewState.LoginError(
                        url = url,
                        serverInfo = state.serverInfo,
                        login = login,
                        password = password,
                        error = state.e.message ?: state.e.stackTraceToString().lines().firstOrNull() ?: "",
                    )
                }
            }
        }
            .stateIn(AddServerByUrlViewState.EnterUrl("", AddServerByUrlViewState.UrlError.None, true))

    fun onClickBack() = close()

    fun onServerUrlChanged(url: String) {
        serverUrl.value = url
    }

    fun onLoginChanged(login: String) {
        this.login.value = login
    }

    fun onPasswordChanged(password: String) {
        this.password.value = password
    }

    fun onClickEdit() {
        internalState.value = InternalState.EnterUrl
    }

    fun onClickCheckConnection() {
        check(internalState.value == InternalState.EnterUrl)
        internalState.value = InternalState.CheckConnection
        launch {
            val url = (createUrl() as UrlResult.SuccessUrl?)?.url ?: error("Can't parse server url")
            val response = serverInfoInteractor.getServerInfo(url)
            when (response) {
                is SafeResponse.Success -> {
                    internalState.value = InternalState.EnterCredentials(response.value)
                }

                is SafeResponse.UnknownError -> {
                    logger.w(response.error) { "Error while getting server params" }
                    internalState.value = InternalState.ConnectionError(response.error)
                }
            }
        }
    }

    fun onClickLogin() {
        val state = internalState.value
        check(state is InternalState.EnterCredentials)
        internalState.value = InternalState.CheckCredentials(state.serverInfo)

        launch {
            val url = (createUrl() as UrlResult.SuccessUrl?)?.url ?: error("Can't parse server url")
            val loginResult = serverAuthInteractor.login(url, login.value, password.value)
            when (loginResult) {
                ServerAuthResult.IncorrectLoginOrPassword -> TODO()
                is ServerAuthResult.NetworkError -> {
                    internalState.value = InternalState.LoginError(state.serverInfo, loginResult.error)
                }

                is ServerAuthResult.Success -> {
                    val server = Server(
                        name = state.serverInfo.name,
                        accessToken = loginResult.accessToken,
                    )
                    serversInteractor.add(server)
                    close()
                }
            }
        }
    }

    fun onSslErrorClickBack() {
        check(internalState.value is InternalState.ConnectionError)
        internalState.value = InternalState.EnterUrl
    }

    private fun createUrl(): UrlResult {
        val stringUrl = serverUrl.value
        if (stringUrl.isEmpty()) {
            return UrlResult.EmptyUrl
        }
        return runCatching { Url(SCHEME + stringUrl) }
            .map { url ->
                if (url.encodedPathAndQuery.isEmpty()) {
                    UrlResult.SuccessUrl(url)
                } else {
                    UrlResult.UrlWithPathOrQuery
                }
            }
            .getOrElse { UrlResult.MalformedUrl }
    }

    private fun UrlResult.toUi() = when (this) {
        is UrlResult.EmptyUrl,
        is UrlResult.SuccessUrl,
        -> AddServerByUrlViewState.UrlError.None

        is UrlResult.UrlWithPathOrQuery -> AddServerByUrlViewState.UrlError.UrlWithPathOrQuery
        is UrlResult.MalformedUrl -> AddServerByUrlViewState.UrlError.MalformedUrl
    }

    private fun UrlResult.isValid() = when (this) {
        is UrlResult.SuccessUrl -> true
        is UrlResult.EmptyUrl,
        is UrlResult.UrlWithPathOrQuery,
        is UrlResult.MalformedUrl,
        -> false
    }

    fun onLoginErrorClickBack() {
        val state = internalState.value
        check(state is InternalState.LoginError)
        internalState.value = InternalState.EnterCredentials(state.serverInfo)
    }

    /**
     * @property EmptyUrl пустой урл.
     * @property SuccessUrl успешная проверка.
     * @property UrlWithPathOrQuery url содержит path что не допустимо как адрес сервера.
     * @property MalformedUrl не валидный url.
     */
    private sealed interface UrlResult {
        data object EmptyUrl : UrlResult
        data class SuccessUrl(val url: Url) : UrlResult
        data object UrlWithPathOrQuery : UrlResult
        data object MalformedUrl : UrlResult
    }

    private sealed interface InternalState {
        data object EnterUrl : InternalState
        data object CheckConnection : InternalState
        data class ConnectionError(val e: Exception) : InternalState
        data class EnterCredentials(val serverInfo: ServerInfo) : InternalState
        data class CheckCredentials(val serverInfo: ServerInfo) : InternalState
        data class LoginError(val serverInfo: ServerInfo, val e: Exception) : InternalState
    }

    companion object {
        private const val SERVER_URL_KEY = "server_url"
        private const val LOGIN_KEY = "login"
        const val SCHEME = "http://"
    }
}
