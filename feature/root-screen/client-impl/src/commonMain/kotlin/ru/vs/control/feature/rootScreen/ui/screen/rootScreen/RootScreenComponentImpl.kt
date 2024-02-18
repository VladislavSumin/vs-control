package ru.vs.control.feature.rootScreen.ui.screen.rootScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.first
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenFactory
import ru.vs.core.splash.childSplash

internal class RootScreenComponentImpl(
    private val rootScreenViewModelFactory: RootScreenViewModelFactory,
    splashScreenFactory: SplashScreenFactory,
    context: ComponentContext,
) : RootScreenComponent, ComponentContext by context {
    private val viewModel = instanceKeeper.getOrCreate { rootScreenViewModelFactory.create() }

    private val splash = childSplash(
        awaitInitialization = {
            viewModel.state.first { it is RootScreenState.Content }
        },
        splashComponentFactory = splashScreenFactory::create,
        contentComponentFactory = { onContentReady, context ->
            viewModel.getContentScreenFactory().create(onContentReady, context)
        },
    )

    @Composable
    override fun Render(modifier: Modifier) {
        val state by splash.subscribeAsState()
        state.current?.Render(modifier)
    }
}
