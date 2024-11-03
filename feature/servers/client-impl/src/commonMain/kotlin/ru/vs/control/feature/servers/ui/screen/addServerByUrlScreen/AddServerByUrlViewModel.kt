package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Stable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import ru.vs.core.decompose.ViewModel
import ru.vs.core.factoryGenerator.GenerateFactory

@Stable
@GenerateFactory
internal class AddServerByUrlViewModel : ViewModel() {
    val state: StateFlow<AddServerByUrlViewState> = TODO()
    val events: Channel<AddServerByUrlEvents> = TODO()
}
