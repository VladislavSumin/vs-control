package ru.vs.control.feature.embeddedServer.ui.component.embeddedServersListComponent

import com.arkivanov.decompose.ComponentContext

interface EmbeddedServersListComponentFactory {
    fun create(context: ComponentContext): EmbeddedServersListComponent
}
