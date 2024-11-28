package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Stable
import io.ktor.http.URLParserException
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
                    val isUrlValid = createUrl() != null
                    AddServerByUrlViewState.EnterUrl(
                        url = url,
                        isShowIncorrectUrlError = !isUrlValid,
                        isCheckConnectionButtonEnabled = isUrlValid && url.isNotEmpty(),
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
            .stateIn(AddServerByUrlViewState.EnterUrl("", false, true))

    fun onClickBack() = close()

    fun onServerUrlChanged(url: String) {
        serverUrl.value = url
    }

    fun onClickCheckConnection() {
        check(internalState.value == InternalState.EnterUrl)
        internalState.value = InternalState.CheckConnection
        launch {
            val response = serverInfoInteractor.getServerInfo(createUrl() ?: return@launch)
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

    private fun createUrl(): Url? {
        return try {
            Url("https://" + serverUrl.value)
        } catch (_: URLParserException) {
            null
        }
    }

    private sealed interface InternalState {
        data object EnterUrl : InternalState
        data object CheckConnection : InternalState
        data class ConnectionError(val e: Exception) : InternalState
        data object EnterCredentials : InternalState
    }

    companion object {
        private const val SERVER_URL_KEY = "server_url"
    }
}
