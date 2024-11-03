package ru.vs.control.feature.servers.ui.screen.addServerScreen

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.feature.servers.ui.screen.addServerScreen.items.AddServerItem
import ru.vs.core.navigation.viewModel.NavigationViewModel

internal class AddServerViewModelFactory {
    fun create(): AddServerViewModel {
        return AddServerViewModel()
    }
}

@Stable
internal class AddServerViewModel : NavigationViewModel() {
    val state: StateFlow<AddServerViewState> = MutableStateFlow(
        AddServerViewState(
            items = listOf(
                AddServerItem.AddServerByUrl,
                AddServerItem.AddServerByQrCode,
                AddServerItem.AddLocalServer,
            ),
        ),
    )

    fun onClickBack() = close()
}
