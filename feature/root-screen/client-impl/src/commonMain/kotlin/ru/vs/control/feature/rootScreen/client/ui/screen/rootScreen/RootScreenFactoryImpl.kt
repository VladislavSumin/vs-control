package ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen

import kotlinx.coroutines.channels.ReceiveChannel
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenFactory
import ru.vs.core.decompose.context.VsComponentContext

internal class RootScreenFactoryImpl(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
    private val splashScreenFactory: SplashScreenFactory,
) : RootScreenFactory {
    override fun create(context: VsComponentContext, deeplink: ReceiveChannel<String>): RootScreenComponent {
        return RootScreenComponent(
            rootScreenViewModelFactory,
            splashScreenFactory,
            context,
            deeplink,
        )
    }
}
