package ru.vs.control.entities.ui.entities

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface EntitiesComponentFactory {
    fun create(componentContext: ComponentContext): ComposeComponent
}
