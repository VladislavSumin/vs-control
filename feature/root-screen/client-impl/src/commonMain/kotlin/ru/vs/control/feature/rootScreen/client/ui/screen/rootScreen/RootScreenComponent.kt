package ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.channels.ReceiveChannel
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenFactory
import ru.vs.core.coroutines.mapState
import ru.vs.core.decompose.context.VsComponent
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.sharedElementTransition.ProvideLocalSharedElementTransition
import ru.vs.core.splash.Children
import ru.vs.core.splash.childSplash

@GenerateFactory(RootScreenFactory::class)
internal class RootScreenComponent(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
    splashScreenFactory: SplashScreenFactory,
    context: VsComponentContext,
    private val deeplink: ReceiveChannel<String>,
) : VsComponent(context), ComposeComponent {
    private val viewModel = viewModel { rootScreenViewModelFactory.create() }

    private val splash = context.childSplash(
        isInitialized = viewModel.state.mapState {
            when (it) {
                RootScreenState.Content -> true
                RootScreenState.Splash -> false
            }
        },
        splashComponentFactory = splashScreenFactory::create,
        contentComponentFactory = { onContentReady, context ->
            viewModel.getContentScreenFactory().create(onContentReady, deeplink, context)
        },
    )

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    override fun Render(modifier: Modifier) {
        SharedTransitionLayout(modifier) {
            Children(splash) {
                ProvideLocalSharedElementTransition(this) {
                    it.Render(Modifier.fillMaxSize())
                }
            }
        }
    }
}
