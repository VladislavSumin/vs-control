package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.vs.core.decompose.createCoroutineScope
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
) : Screen, ScreenContext by context {
    private val viewModel = instanceKeeper.getOrCreate { viewModelFactory.create() }

    init {
        // TODO упростить навигацию
        val scope = lifecycle.createCoroutineScope()
        scope.launch {
            viewModel.navigationChannel.receiveAsFlow().collect(navigator::open)
        }
    }

    @Composable
    override fun Render(modifier: Modifier) = WelcomeScreenContent(viewModel, modifier)
}
