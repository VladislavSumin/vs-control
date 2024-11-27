package ru.vs.control.feature.embeddedServer.client.ui.screen.addEmbeddedServerScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class AddEmbeddedServerScreen(
    viewModelFactory: AddEmbeddedServerViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        AddEmbeddedServerContent(viewModel, modifier)
    }
}
