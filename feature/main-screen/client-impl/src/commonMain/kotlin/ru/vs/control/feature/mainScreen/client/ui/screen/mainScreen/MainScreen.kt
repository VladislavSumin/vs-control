package ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.pages.Pages
import ru.vladislavsumin.core.navigation.host.childNavigationPages
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.screen.ScreenContext
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenParams
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory

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
