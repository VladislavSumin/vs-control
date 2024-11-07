package ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen

import androidx.compose.runtime.Stable
import ru.vs.control.feature.embeddedServer.domain.EmbeddedServersInteractor
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

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
