package ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.childContext
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramComponentFactory
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class DebugScreen(
    viewModelFactory: DebugViewModelFactory,
    umlDiagramComponentFactory: NavigationGraphUmlDiagramComponentFactory<VsComponentContext>,
    context: VsComponentContext,
) : VsScreen(context) {

    val viewModel = viewModel { viewModelFactory.create() }

    private val umlDiagramComponent = umlDiagramComponentFactory.create(
        context.childContext("uml"),
        navigationTreeInterceptor = { viewModel.generateFakeNavigationNodesFist(it) },
    )

    @Composable
    override fun Render(modifier: Modifier) = DebugContent(viewModel, umlDiagramComponent, modifier)
}
