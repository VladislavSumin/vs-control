package ru.vs.control.feature.servers.ui.screen.serversScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class ServersScreen(
    viewModelFactory: ServersViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = ServersContent(viewModel, modifier)
}
