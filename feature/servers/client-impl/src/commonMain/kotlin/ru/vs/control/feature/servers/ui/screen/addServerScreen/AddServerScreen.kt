package ru.vs.control.feature.servers.ui.screen.addServerScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.operator.map
import ru.vs.control.feature.servers.ui.screen.addServerScreen.items.AddServerItem
import ru.vs.control.feature.servers.ui.screen.addServerScreen.items.SimpleAddServerItemComponent
import ru.vs.control.feature.servers.ui.screen.addServerScreen.items.localSearch.LocalSearchAddServerComponent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.router.list.childList
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class AddServerScreenFactory(
    private val viewModelFactory: AddServerViewModelFactory,
) : ScreenFactory<AddServerScreenParams, AddServerScreen> {
    override fun create(context: ScreenContext, params: AddServerScreenParams): AddServerScreen {
        return AddServerScreen(viewModelFactory, context, params)
    }
}

internal class AddServerScreen(
    viewModelFactory: AddServerViewModelFactory,
    context: ScreenContext,
    params: AddServerScreenParams,
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
        is AddServerItem.Simple -> SimpleAddServerItemComponent(configuration, context)
        is AddServerItem.SearchServersInLocalNetwork -> LocalSearchAddServerComponent(context)
    }

    @Composable
    override fun Render(modifier: Modifier) = AddServerContent(modifier, viewModel, list)
}
