package ru.vs.control.feature.settingsScreen.client.ui.screen.settingsScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class SettingsScreen(
    viewModelFactory: SettingsViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = SettingsContent(modifier)
}
