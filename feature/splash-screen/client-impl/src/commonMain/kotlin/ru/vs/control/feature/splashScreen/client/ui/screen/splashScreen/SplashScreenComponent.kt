package ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory
import ru.vs.core.decompose.context.VsComponent
import ru.vs.core.decompose.context.VsComponentContext

@GenerateFactory(SplashScreenFactory::class)
internal class SplashScreenComponent(
    splashScreenViewModelFactory: SplashScreenViewModelFactory,
    context: VsComponentContext,
) : VsComponent(context), ComposeComponent {
    private val viewModel = viewModel { splashScreenViewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = SplashScreenContent(viewModel, modifier)
}
