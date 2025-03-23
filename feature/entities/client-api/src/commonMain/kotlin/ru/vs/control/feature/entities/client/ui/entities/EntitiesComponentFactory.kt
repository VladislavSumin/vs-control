package ru.vs.control.feature.entities.client.ui.entities

import com.arkivanov.decompose.ComponentContext
import ru.vladislavsumin.core.decompose.compose.ComposeComponent

interface EntitiesComponentFactory {
    fun create(componentContext: ComponentContext): ComposeComponent
}
