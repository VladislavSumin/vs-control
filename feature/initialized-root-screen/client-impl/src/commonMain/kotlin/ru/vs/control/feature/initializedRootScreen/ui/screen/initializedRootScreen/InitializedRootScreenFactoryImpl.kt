package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext
import ru.vs.navigation.NavigationGraph

internal class InitializedRootScreenFactoryImpl(
    private val navigationGraph: NavigationGraph,
) : InitializedRootScreenFactory {
    override fun create(
        onContentReady: () -> Unit,
        componentContext: ComponentContext,
    ): InitializedRootScreenComponent {
        return InitializedRootScreenComponentImpl(navigationGraph, onContentReady, componentContext)
    }
}
