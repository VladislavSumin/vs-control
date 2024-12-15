package ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent

import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.control.feature.embeddedServer.client.domain.EmbeddedServersInteractor
import ru.vs.control.feature.embeddedServer.client.repository.EmbeddedServer
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
