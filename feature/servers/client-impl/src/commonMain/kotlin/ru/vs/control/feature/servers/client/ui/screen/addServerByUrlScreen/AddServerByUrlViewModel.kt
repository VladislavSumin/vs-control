package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Stable
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.ktor.client.SafeResponse
import ru.vs.core.logger.api.logger
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class AddServerByUrlViewModel(
    private val serverInfoInteractor: ServerInfoInteractor,
) : NavigationViewModel() {
    private val logger = logger("AddServerByUrl")

    private val serverUrl = saveableStateFlow(SERVER_URL_KEY, "")

    private val internalState = MutableStateFlow<InternalState>(InternalState.EnterUrl)

    val state: StateFlow<AddServerByUrlViewState> =
        combine(
            internalState,
            serverUrl,
        ) { state, url ->
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

                is InternalState.EnterCredentials -> AddServerByUrlViewState.EnterCredentials(url)
            }
        }
            .stateIn(AddServerByUrlViewState.EnterUrl("", AddServerByUrlViewState.UrlError.None, true))

    fun onClickBack() = close()

    fun onServerUrlChanged(url: String) {
        serverUrl.value = url
    }

    fun onClickCheckConnection() {
        check(internalState.value == InternalState.EnterUrl)
        internalState.value = InternalState.CheckConnection
        launch {
            val url = (createUrl() as UrlResult.SuccessUrl?)?.url ?: return@launch
            val response = serverInfoInteractor.getServerInfo(url)
            when (response) {
                is SafeResponse.Success -> {
                    internalState.value = InternalState.EnterCredentials
                }

                is SafeResponse.UnknownError -> {
                    logger.w(response.error) { "Error while getting server params" }
                    internalState.value = InternalState.ConnectionError(response.error)
                }
            }
        }
    }

    fun onClickLogin() {
        // TODO добавить логин.
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
        data object EnterCredentials : InternalState
    }

    companion object {
        private const val SERVER_URL_KEY = "server_url"
        const val SCHEME = "http://"
    }
}
