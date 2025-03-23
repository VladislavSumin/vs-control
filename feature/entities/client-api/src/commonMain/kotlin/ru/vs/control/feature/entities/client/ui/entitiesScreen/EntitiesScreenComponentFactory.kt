package ru.vs.control.feature.entities.client.ui.entitiesScreen

import com.arkivanov.decompose.ComponentContext
import ru.vladislavsumin.core.decompose.compose.ComposeComponent

interface EntitiesScreenComponentFactory {
    fun create(componentContext: ComponentContext): ComposeComponent
}
