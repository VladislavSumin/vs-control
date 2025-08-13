package ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent

import ru.vs.core.decompose.LazyListComponent
import ru.vs.core.decompose.context.VsComponentContext

internal class EmbeddedServersListComponentFactoryImpl(
    private val viewModelFactory: EmbeddedServersListViewModelFactory,
) : EmbeddedServersListComponentFactory {
    override fun create(context: VsComponentContext): LazyListComponent {
        return EmbeddedServersListComponent(viewModelFactory, context)
    }
}
