package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Stable
import ru.vs.core.factoryGenerator.GenerateFactory
import ru.vs.core.navigation.viewModel.NavigationViewModel

@Stable
@GenerateFactory
internal class AddServerByUrlViewModel : NavigationViewModel() {
//    val state: StateFlow<AddServerByUrlViewState> = TODO()
//    val events: Channel<AddServerByUrlEvents> = TODO()

    fun onClickBack() = close()
}
