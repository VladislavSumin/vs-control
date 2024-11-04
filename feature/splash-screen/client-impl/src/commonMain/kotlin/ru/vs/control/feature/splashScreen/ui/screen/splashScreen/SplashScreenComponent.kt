package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.core.decompose.Component

internal class SplashScreenComponent(
    splashScreenViewModelFactory: SplashScreenViewModelFactory,
    context: ComponentContext,
) : Component(context) {
    private val viewModel = instanceKeeper.getOrCreate { splashScreenViewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = SplashScreenContent(viewModel, modifier)
}
