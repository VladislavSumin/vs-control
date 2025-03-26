package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory

@GenerateScreenFactory
internal class AddServerByUrlScreen(
    viewModelFactory: AddServerByUrlViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        AddServerByUrlContent(viewModel, modifier)
    }
}
