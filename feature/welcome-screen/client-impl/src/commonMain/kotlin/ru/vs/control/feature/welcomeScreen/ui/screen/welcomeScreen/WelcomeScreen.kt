package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class WelcomeScreenFactory(
    private val viewModelFactory: WelcomeScreenViewModelFactory,
) : ScreenFactory<WelcomeScreenParams, WelcomeScreen> {
    override fun create(context: ScreenContext, params: WelcomeScreenParams): WelcomeScreen {
        return WelcomeScreen(viewModelFactory, context)
    }
}

internal class WelcomeScreen(
    viewModelFactory: WelcomeScreenViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    // TODO добавить в базовый экран делегат by viewModel + правила detekt на запрет прямого использования keeper

    @Composable
    override fun Render(modifier: Modifier) = WelcomeScreenContent(viewModel, modifier)
}
