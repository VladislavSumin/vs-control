package ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class SettingsScreen(
    viewModelFactory: SettingsViewModelFactory,
    context: VsComponentContext,
) : VsScreen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = SettingsContent(modifier)
}
