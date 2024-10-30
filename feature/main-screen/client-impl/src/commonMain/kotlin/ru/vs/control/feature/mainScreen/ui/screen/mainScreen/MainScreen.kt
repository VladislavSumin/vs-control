package ru.vs.control.feature.mainScreen.ui.screen.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class MainScreenFactory(
    private val viewModelFactory: MainViewModelFactory,
) : ScreenFactory<MainScreenParams, MainScreen> {
    override fun create(context: ScreenContext, params: MainScreenParams): MainScreen {
        return MainScreen(viewModelFactory, context, params)
    }
}

internal class MainScreen(
    viewModelFactory: MainViewModelFactory,
    context: ScreenContext,
    params: MainScreenParams,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        MainContent(viewModel, modifier)
    }
}
