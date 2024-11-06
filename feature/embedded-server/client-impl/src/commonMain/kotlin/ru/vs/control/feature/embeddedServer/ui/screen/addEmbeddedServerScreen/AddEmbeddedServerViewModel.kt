package ru.vs.control.feature.embeddedServer.ui.screen.addEmbeddedServerScreen

import androidx.compose.runtime.Stable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import ru.vs.core.decompose.ViewModel
import ru.vs.core.factoryGenerator.GenerateFactory

@Stable
@GenerateFactory
internal class AddEmbeddedServerViewModel : ViewModel() {
    val state: StateFlow<AddEmbeddedServerViewState> = TODO()
    val events: Channel<AddEmbeddedServerEvents> = TODO()
}
