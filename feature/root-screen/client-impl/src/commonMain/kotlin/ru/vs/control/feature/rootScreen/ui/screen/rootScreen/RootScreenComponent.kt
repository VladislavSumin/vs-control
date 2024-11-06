package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.first
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenFactory
import ru.vs.core.decompose.Component
import ru.vs.core.sharedElementTransition.ProvideLocalSharedElementTransition
import ru.vs.core.splash.Children
import ru.vs.core.splash.childSplash

internal class RootScreenComponent(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
    splashScreenFactory: SplashScreenFactory,
    context: ComponentContext,
    private val deeplink: ReceiveChannel<String>,
) : Component(context) {
    private val viewModel = viewModel { rootScreenViewModelFactory.create() }

    private val splash = childSplash(
        awaitInitialization = { viewModel.state.first { it is RootScreenState.Content } },
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