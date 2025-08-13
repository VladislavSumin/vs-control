package ru.vs.control.feature.servers.client.ui.screen.addServerScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.value.operator.map
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items.AddServerItem
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items.SimpleAddServerItemComponent
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items.localSearch.LocalSearchAddServerComponent
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen
import ru.vs.core.decompose.router.list.childList

@GenerateScreenFactory
internal class AddServerScreen(
    viewModelFactory: AddServerViewModelFactory,
    context: VsComponentContext,
) : VsScreen(context) {
    @Suppress("UnusedPrivateProperty")
    private val viewModel = viewModel { viewModelFactory.create() }

    /**
     * Список способов подключения к серверу.
     */
    private val list = context.childList(
        state = viewModel.state.asValue().map { it.items },
        childFactory = ::createConnectToServerComponent,
    )

    @Suppress("UnusedParameter")
    private fun createConnectToServerComponent(
        configuration: AddServerItem,
        context: VsComponentContext,
    ): ComposeComponent = when (configuration) {
        is AddServerItem.Simple -> SimpleAddServerItemComponent(viewModel, configuration)
        is AddServerItem.SearchServersInLocalNetwork -> LocalSearchAddServerComponent()
    }

    @Composable
    override fun Render(modifier: Modifier) = AddServerContent(modifier, viewModel, list)
}
