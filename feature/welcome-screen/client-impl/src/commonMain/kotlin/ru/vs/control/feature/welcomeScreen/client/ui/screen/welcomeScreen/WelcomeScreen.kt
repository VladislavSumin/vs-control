package ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class WelcomeScreen(
    viewModelFactory: WelcomeScreenViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = WelcomeScreenContent(viewModel, modifier)
}
