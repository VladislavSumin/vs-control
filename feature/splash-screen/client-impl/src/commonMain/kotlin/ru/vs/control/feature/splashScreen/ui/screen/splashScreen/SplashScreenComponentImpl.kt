package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenComponent

internal class SplashScreenComponentImpl(
    splashScreenViewModelFactory: SplashScreenViewModelFactory,
    context: ComponentContext,
) : SplashScreenComponent, ComponentContext by context {
    private val viewModel = instanceKeeper.getOrCreate { splashScreenViewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        SplashScreenContent(viewModel, modifier)
    }
}
