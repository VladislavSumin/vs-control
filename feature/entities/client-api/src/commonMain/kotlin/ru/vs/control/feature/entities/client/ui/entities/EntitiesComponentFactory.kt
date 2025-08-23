package ru.vs.control.feature.entities.client.ui.entities

import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.core.decompose.context.VsComponentContext

interface EntitiesComponentFactory {
    fun create(componentContext: VsComponentContext): ComposeComponent
}
