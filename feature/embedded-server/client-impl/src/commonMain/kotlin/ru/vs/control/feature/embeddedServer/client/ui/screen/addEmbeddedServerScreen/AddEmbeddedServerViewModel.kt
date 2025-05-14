package ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen

import androidx.compose.runtime.Stable
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServersInteractor
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServer

@Stable
@GenerateFactory
internal class AddEmbeddedServerViewModel(
    private val embeddedServersInteractor: EmbeddedServersInteractor,
) : NavigationViewModel() {
    val serverName = saveableStateFlow(SERVER_NAME_KEY, "")

    fun onServerNameChanged(name: String) {
        serverName.value = name
    }

    fun onClickSave() = launch {
        embeddedServersInteractor.add(
            EmbeddedServer(
                name = serverName.value,
            ),
        )
        close()
    }

    fun onClickBack() = close()

    companion object {
        private const val SERVER_NAME_KEY = "server_name"
    }
}
