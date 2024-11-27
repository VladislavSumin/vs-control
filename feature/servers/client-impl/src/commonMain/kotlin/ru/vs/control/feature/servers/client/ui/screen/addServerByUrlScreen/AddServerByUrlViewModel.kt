package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Stable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class AddServerByUrlViewModel : NavigationViewModel() {

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
            // TODO тестовая задержка пока нет реальной проверки подключения
            @Suppress("MagicNumber")
            delay(500)
            // internalState.value = InternalState.EnterCredentials
            internalState.value = InternalState.SslError
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
