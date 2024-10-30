package ru.vs.control.feature.servers.ui.screen.addServerScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class AddServerScreenFactory(
    private val viewModelFactory: AddServerViewModelFactory,
) : ScreenFactory<AddServerScreenParams, AddServerScreen> {
    override fun create(context: ScreenContext, params: AddServerScreenParams): AddServerScreen {
        return AddServerScreen(viewModelFactory, context, params)
    }
}

internal class AddServerScreen(
    viewModelFactory: AddServerViewModelFactory,
    context: ScreenContext,
    params: AddServerScreenParams,
) : Screen(context) {
    @Suppress("UnusedPrivateProperty")
    private val viewModel = instanceKeeper.getOrCreate { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        AddServerContent(modifier)
    }
}
