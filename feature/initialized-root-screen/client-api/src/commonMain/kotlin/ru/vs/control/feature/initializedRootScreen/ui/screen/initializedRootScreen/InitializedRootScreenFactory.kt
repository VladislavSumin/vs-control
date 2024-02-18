package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext

interface InitializedRootScreenFactory {
    fun create(componentContext: ComponentContext): InitializedRootScreenComponent
}
