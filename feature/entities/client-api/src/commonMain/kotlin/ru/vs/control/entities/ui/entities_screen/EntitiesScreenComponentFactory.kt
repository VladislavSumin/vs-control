package ru.vs.control.entities.ui.entities_screen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface EntitiesScreenComponentFactory {
    fun create(componentContext: ComponentContext): ComposeComponent
}
