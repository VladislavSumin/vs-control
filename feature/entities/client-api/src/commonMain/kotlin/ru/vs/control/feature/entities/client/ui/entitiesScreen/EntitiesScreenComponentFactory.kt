package ru.vs.control.feature.entities.client.ui.entitiesScreen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface EntitiesScreenComponentFactory {
    fun create(componentContext: ComponentContext): ComposeComponent
}
