package ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vladislavsumin.core.decompose.components.Component
import ru.vs.core.decompose.ComposeComponent

internal class SplashScreenComponent(
    splashScreenViewModelFactory: SplashScreenViewModelFactory,
    context: ComponentContext,
) : Component(context), ComposeComponent {
    private val viewModel = viewModel { splashScreenViewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = SplashScreenContent(viewModel, modifier)
}
