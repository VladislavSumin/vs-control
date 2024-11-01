package ru.vs.control.feature.initializedRootScreen.ui.screen.initializedRootScreen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.ReceiveChannel
import ru.vs.core.navigation.Navigation

internal class InitializedRootScreenFactoryImpl(
    private val navigation: Navigation,
) : InitializedRootScreenFactory {
    override fun create(
        onContentReady: () -> Unit,
        deeplink: ReceiveChannel<String>,
        componentContext: ComponentContext,
    ): InitializedRootScreenComponent {
        return InitializedRootScreenComponentImpl(navigation, onContentReady, deeplink, componentContext)
    }
}
