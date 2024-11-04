package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.Component

internal class SplashScreenComponent(
    splashScreenViewModelFactory: SplashScreenViewModelFactory,
    context: ComponentContext,
) : Component(context) {
    private val viewModel = viewModel { splashScreenViewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = SplashScreenContent(viewModel, modifier)
}
