package ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramComponentFactory

@GenerateScreenFactory
internal class DebugScreen(
    viewModelFactory: DebugViewModelFactory,
    umlDiagramComponentFactory: NavigationGraphUmlDiagramComponentFactory,
    context: ComponentContext,
) : Screen(context) {

    val viewModel = viewModel { viewModelFactory.create() }

    private val umlDiagramComponent = umlDiagramComponentFactory.create(
        context.childContext("uml"),
        navigationTreeInterceptor = { viewModel.generateFakeNavigationNodesFist(it) },
    )

    @Composable
    override fun Render(modifier: Modifier) = DebugContent(viewModel, umlDiagramComponent, modifier)
}
