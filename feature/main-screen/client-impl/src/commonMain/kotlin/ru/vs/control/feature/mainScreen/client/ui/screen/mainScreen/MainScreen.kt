package ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.pages.Pages
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenParams
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.host.childNavigationPages
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class MainScreen(
    viewModelFactory: MainViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    private val tabNavigation = childNavigationPages(
        navigationHost = TabNavigationHost,
        initialPages = { Pages(listOf(ServersScreenParams), 0) },
    )

    @Composable
    override fun Render(modifier: Modifier) = MainContent(viewModel, tabNavigation, modifier)
}
