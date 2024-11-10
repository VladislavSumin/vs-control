package ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent

import ru.vs.control.feature.embeddedServer.domain.EmbeddedServersInteractor
import ru.vs.control.feature.embeddedServer.repository.EmbeddedServer
import ru.vs.core.decompose.ViewModel
import ru.vs.core.factoryGenerator.GenerateFactory

@GenerateFactory
internal class EmbeddedServersListViewModel(
    private val embeddedServersInteractor: EmbeddedServersInteractor,
) : ViewModel() {
    val state = embeddedServersInteractor.observeEmbeddedServers()
        .stateIn(emptyList())

    fun onClickDelete(item: EmbeddedServer) = launch {
        embeddedServersInteractor.delete(item)
    }
}
