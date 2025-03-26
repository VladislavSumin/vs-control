package ru.vs.control.feature.servers.client.ui.screen.addServerScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.operator.map
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.screen.ScreenContext
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items.AddServerItem
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items.SimpleAddServerItemComponent
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items.localSearch.LocalSearchAddServerComponent
import ru.vs.core.decompose.router.list.childList
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory

@GenerateScreenFactory
internal class AddServerScreen(
    viewModelFactory: AddServerViewModelFactory,
    context: ScreenContext,
) : Screen(context) {
    @Suppress("UnusedPrivateProperty")
    private val viewModel = viewModel { viewModelFactory.create() }

    /**
     * Список способов подключения к серверу.
     */
    private val list = childList(
        state = viewModel.state.asValue().map { it.items },
        childFactory = ::createConnectToServerComponent,
    )

    private fun createConnectToServerComponent(
        configuration: AddServerItem,
        context: ComponentContext,
    ): ComposeComponent = when (configuration) {
        is AddServerItem.Simple -> SimpleAddServerItemComponent(viewModel, configuration, context)
        is AddServerItem.SearchServersInLocalNetwork -> LocalSearchAddServerComponent(context)
    }

    @Composable
    override fun Render(modifier: Modifier) = AddServerContent(modifier, viewModel, list)
}
