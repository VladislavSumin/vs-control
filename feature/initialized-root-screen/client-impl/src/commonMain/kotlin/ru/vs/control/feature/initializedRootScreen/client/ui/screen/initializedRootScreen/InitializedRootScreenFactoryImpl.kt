package ru.vs.control.feature.initializedRootScreen.client.ui.screen.initializedRootScreen

import kotlinx.coroutines.channels.ReceiveChannel
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.core.decompose.context.VsComponentContext

internal class InitializedRootScreenFactoryImpl(
    private val viewModelFactory: InitializedRootViewModelFactory,
) : InitializedRootScreenFactory {
    override fun create(
        onContentReady: () -> Unit,
        deeplink: ReceiveChannel<String>,
        componentContext: VsComponentContext,
    ): ComposeComponent {
        return InitializedRootScreenComponent(viewModelFactory, onContentReady, deeplink, componentContext)
    }
}
