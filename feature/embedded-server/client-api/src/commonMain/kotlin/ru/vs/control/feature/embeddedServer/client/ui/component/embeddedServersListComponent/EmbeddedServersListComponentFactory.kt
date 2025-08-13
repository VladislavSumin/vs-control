package ru.vs.control.feature.embeddedServer.client.ui.component.embeddedServersListComponent

import ru.vs.core.decompose.LazyListComponent
import ru.vs.core.decompose.context.VsComponentContext

interface EmbeddedServersListComponentFactory {
    fun create(context: VsComponentContext): LazyListComponent
}
