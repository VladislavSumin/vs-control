package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen

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

    private val serverUrl = MutableStateFlow("")
    private val internalState = MutableStateFlow<InternalState>(InternalState.EnterUrl)

    val state: StateFlow<AddServerByUrlViewState> =
        combine(
            internalState,
            serverUrl,
        ) { state, url ->
            when (state) {
                InternalState.EnterUrl -> AddServerByUrlViewState.EnterUrl(url)
                InternalState.CheckConnection -> AddServerByUrlViewState.CheckConnection(url)
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
            internalState.value = InternalState.EnterCredentials
        }
    }

    private enum class InternalState {
        EnterUrl,
        CheckConnection,
        EnterCredentials,
    }
}
