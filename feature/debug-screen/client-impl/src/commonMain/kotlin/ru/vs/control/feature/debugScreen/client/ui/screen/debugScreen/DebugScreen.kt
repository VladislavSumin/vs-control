package ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.childContext
import ru.vladislavsumin.core.navigation.screen.Screen
import ru.vladislavsumin.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.ui.debug.uml.NavigationGraphUmlDiagramComponentFactory

@GenerateScreenFactory
internal class DebugScreen(
    viewModelFactory: DebugViewModelFactory,
    umlDiagramComponentFactory: NavigationGraphUmlDiagramComponentFactory,
    context: ScreenContext,
) : Screen(context) {

    val viewModel = viewModel { viewModelFactory.create() }

    private val umlDiagramComponent = umlDiagramComponentFactory.create(
        childContext("uml"),
        navigationTreeInterceptor = { viewModel.generateFakeNavigationNodesFist(it) },
    )

    @Composable
    override fun Render(modifier: Modifier) = DebugContent(viewModel, umlDiagramComponent, modifier)
}
