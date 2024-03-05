package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.navigation.tree.NavigationTree

internal class InitializedRootScreenFactoryImpl(
    private val navigationTree: NavigationTree,
) : InitializedRootScreenFactory {
    override fun create(
        onContentReady: () -> Unit,
        componentContext: ComponentContext,
    ): InitializedRootScreenComponent {
        return InitializedRootScreenComponentImpl(navigationTree, onContentReady, componentContext)
    }
}
