package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext

internal class InitializedRootScreenFactoryImpl : InitializedRootScreenFactory {
    override fun create(componentContext: ComponentContext): InitializedRootScreenComponent {
        return InitializedRootScreenComponentImpl(componentContext)
    }
}
