package ru.vs.control.feature.servers.ui.screen.addServerScreen

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.servers.ui.screen.addServerScreen.items.AddServerItem
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class AddServerViewModel : NavigationViewModel() {
    val state: StateFlow<AddServerViewState> = MutableStateFlow(
        AddServerViewState(
            items = listOf(
                AddServerItem.AddPrebuildServer("Control", "https://control.vs"),
                AddServerItem.AddServerByUrl,
                AddServerItem.AddServerByQrCode,
                AddServerItem.AddLocalServer,
                AddServerItem.SearchServersInLocalNetwork,
            ),
        ),
    )

    fun onClickBack() = close()
}
