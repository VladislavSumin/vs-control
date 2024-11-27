package ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.ReceiveChannel
import ru.vs.core.decompose.ComposeComponent

internal class InitializedRootScreenFactoryImpl(
    private val viewModelFactory: InitializedRootViewModelFactory,
) : InitializedRootScreenFactory {
    override fun create(
        onContentReady: () -> Unit,
        deeplink: ReceiveChannel<String>,
        componentContext: ComponentContext,
    ): ComposeComponent {
        return InitializedRootScreenComponent(viewModelFactory, onContentReady, deeplink, componentContext)
    }
}
