package ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.pages.Pages
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.host.childNavigationPages
import ru.vs.control.feature.servers.client.ui.screen.serversScreen.ServersScreenParams
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class MainScreen(
    viewModelFactory: MainViewModelFactory,
    context: VsComponentContext,
) : VsScreen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    private val tabNavigation = childNavigationPages(
        navigationHost = TabNavigationHost,
        initialPages = { Pages(listOf(ServersScreenParams), 0) },
    )

    @Composable
    override fun Render(modifier: Modifier) = MainContent(viewModel, tabNavigation, modifier)
}
