package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.ReceiveChannel

internal class InitializedRootScreenFactoryImpl(
    private val viewModelFactory: InitializedRootViewModelFactory,
) : InitializedRootScreenFactory {
    override fun create(
        onContentReady: () -> Unit,
        deeplink: ReceiveChannel<String>,
        componentContext: ComponentContext,
    ): InitializedRootScreenComponent {
        return InitializedRootScreenComponentImpl(viewModelFactory, onContentReady, deeplink, componentContext)
    }
}
