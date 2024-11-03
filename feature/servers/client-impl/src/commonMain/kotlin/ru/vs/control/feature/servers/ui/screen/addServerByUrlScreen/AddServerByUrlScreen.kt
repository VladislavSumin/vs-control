package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class AddServerByUrlScreen(
    viewModelFactory: AddServerByUrlViewModelFactory,
    context: ScreenContext,
) : Screen(context), ScreenContext by context {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        AddServerByUrlContent(modifier)
    }
}
