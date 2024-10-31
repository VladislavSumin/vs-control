package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.navigation.Navigation

internal class InitializedRootScreenFactoryImpl(
    private val navigation: Navigation,
) : InitializedRootScreenFactory {
    override fun create(
        onContentReady: () -> Unit,
        componentContext: ComponentContext,
    ): InitializedRootScreenComponent {
        return InitializedRootScreenComponentImpl(navigation, onContentReady, componentContext)
    }
}
