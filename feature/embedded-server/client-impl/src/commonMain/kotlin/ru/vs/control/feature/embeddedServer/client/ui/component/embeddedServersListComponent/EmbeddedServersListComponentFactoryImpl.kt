package ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.LazyListComponent

internal class EmbeddedServersListComponentFactoryImpl(
    private val viewModelFactory: EmbeddedServersListViewModelFactory,
) : EmbeddedServersListComponentFactory {
    override fun create(context: ComponentContext): LazyListComponent {
        return EmbeddedServersListComponent(viewModelFactory, context)
    }
}
