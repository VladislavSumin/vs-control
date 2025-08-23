package ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class EntitiesScreen(
    viewModelFactory: EntitiesViewModelFactory,
    context: VsComponentContext,
    params: EntitiesScreenParams,
) : VsScreen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        EntitiesContent(modifier)
    }
}
