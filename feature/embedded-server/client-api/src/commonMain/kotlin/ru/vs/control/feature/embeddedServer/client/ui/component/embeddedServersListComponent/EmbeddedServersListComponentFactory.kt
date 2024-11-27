package ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.LazyListComponent

interface EmbeddedServersListComponentFactory {
    fun create(context: ComponentContext): LazyListComponent
}
