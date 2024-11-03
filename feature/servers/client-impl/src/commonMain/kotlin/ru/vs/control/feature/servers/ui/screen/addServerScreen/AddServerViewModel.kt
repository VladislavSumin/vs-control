package ru.vs.control.feature.servers.ui.screen.addServerScreen

import androidx.compose.runtime.Stable
import ru.vs.core.decompose.ViewModel

internal class AddServerViewModelFactory {
    fun create(): AddServerViewModel {
        return AddServerViewModel()
    }
}

@Stable
internal class AddServerViewModel : ViewModel() {
//    val state: StateFlow<AddServerViewState> = TODO()
//    val events: Channel<AddServerEvents> = TODO()
}
