package ru.vs.control.feature.entities.client.ui.entities

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface EntitiesComponentFactory {
    fun create(componentContext: ComponentContext): ComposeComponent
}
