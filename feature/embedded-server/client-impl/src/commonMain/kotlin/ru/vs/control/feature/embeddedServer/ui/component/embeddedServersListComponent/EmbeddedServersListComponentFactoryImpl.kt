package ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent

import com.arkivanov.decompose.ComponentContext

internal class EmbeddedServersListComponentFactoryImpl(
    private val viewModelFactory: EmbeddedServersListViewModelFactory,
) : EmbeddedServersListComponentFactory {
    override fun create(context: ComponentContext): EmbeddedServersListComponent {
        return EmbeddedServersListComponentImpl(viewModelFactory, context)
    }
}
