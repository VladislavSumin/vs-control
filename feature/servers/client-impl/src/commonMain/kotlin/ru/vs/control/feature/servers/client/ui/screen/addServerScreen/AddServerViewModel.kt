package ru.vs.control.feature.servers.client.ui.screen.addServerScreen

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vladislavsumin.core.navigation.viewModel.NavigationViewModel
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServerSupportInteractor
import ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen.AddEmbeddedServerScreenParams
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlScreenParams
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items.AddServerItem
import ru.vs.core.factoryGenerator.GenerateFactory

@Stable
@GenerateFactory
internal class AddServerViewModel(
    embeddedServerSupportInteractor: EmbeddedServerSupportInteractor,
) : NavigationViewModel() {
    val state: StateFlow<AddServerViewState> = MutableStateFlow(
        AddServerViewState(
            items = listOf(
                AddServerItem.AddPrebuildServer("Control", "https://control.vs"),
                AddServerItem.AddServerByUrl,
                AddServerItem.AddServerByQrCode,
                AddServerItem.AddEmbeddedServer(embeddedServerSupportInteractor.isSupportedOnCurrentPlatform()),
                AddServerItem.SearchServersInLocalNetwork,
            ),
        ),
    )

    fun onClickBack() = close()

    fun onClickSimpleItem(item: AddServerItem.Simple) {
        when (item) {
            is AddServerItem.AddServerByUrl -> open(AddServerByUrlScreenParams)
            is AddServerItem.AddEmbeddedServer -> open(AddEmbeddedServerScreenParams)
            is AddServerItem.AddServerByQrCode,
            is AddServerItem.AddPrebuildServer,
            -> Unit // not implemented now
        }
    }
}
