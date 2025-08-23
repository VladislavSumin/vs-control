package ru.vs.control.feature.entitiesScreen.client.ui.screen.entitiesScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.childContext
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.control.feature.entities.client.ui.entities.EntitiesComponentFactory
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class EntitiesScreen(
    viewModelFactory: EntitiesViewModelFactory,
    entitiesComponentFactory: EntitiesComponentFactory,
    context: VsComponentContext,
) : VsScreen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }
    private val entitiesComponent = entitiesComponentFactory.create(context.childContext("entities-component"))

    @Composable
    override fun Render(modifier: Modifier) {
        EntitiesContent(modifier, entitiesComponent)
    }
}
