package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Stable
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.io.IOException
import ru.vs.control.feature.serverInfo.client.domain.ServerInfoInteractor
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class AddServerByUrlViewModel(
    private val serverInfoInteractor: ServerInfoInteractor,
) : NavigationViewModel() {

    private val serverUrl = saveableStateFlow(SERVER_URL_KEY, "")

    private val internalState = MutableStateFlow<InternalState>(InternalState.EnterUrl)

    val state: StateFlow<AddServerByUrlViewState> =
        combine(
            internalState,
            serverUrl,
        ) { state, url ->
            when (state) {
                InternalState.EnterUrl -> AddServerByUrlViewState.EnterUrl(url)
                InternalState.CheckConnection -> AddServerByUrlViewState.CheckConnection(url)
                InternalState.SslError -> AddServerByUrlViewState.SslError(url)
                InternalState.EnterCredentials -> AddServerByUrlViewState.EnterCredentials(url)
            }
        }
            .stateIn(AddServerByUrlViewState.EnterUrl(""))

    fun onClickBack() = close()

    fun onServerUrlChanged(url: String) {
        serverUrl.value = url
    }

    fun onClickCheckConnection() {
        check(internalState.value == InternalState.EnterUrl)
        internalState.value = InternalState.CheckConnection
        launch {
            try {
                serverInfoInteractor.getServerInfo(Url(serverUrl.value))
                internalState.value = InternalState.EnterCredentials
            } catch (_: IOException) {
                // TODO продумать общую обработку ошибок при работе с ktor клиентом.
                internalState.value = InternalState.SslError
            }
        }
    }

    fun onClickLogin() {
        // TODO добавить логин.
    }

    fun onSslErrorClickBack() {
        check(internalState.value == InternalState.SslError)
        internalState.value = InternalState.EnterUrl
    }

    private enum class InternalState {
        EnterUrl,
        CheckConnection,
        SslError,
        EnterCredentials,
    }

    companion object {
        private const val SERVER_URL_KEY = "server_url"
    }
}
