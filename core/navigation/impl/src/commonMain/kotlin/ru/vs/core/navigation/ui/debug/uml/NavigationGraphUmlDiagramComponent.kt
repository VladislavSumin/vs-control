package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.core.decompose.ComposeComponent

class NavigationGraphUmlDiagramComponentFactory internal constructor(
    private val viewModelFactory: NavigationGraphUmlDiagramViewModelFactory,
) {
    fun create(context: ComponentContext): ComposeComponent {
        return NavigationGraphUmlDiagramComponent(
            viewModelFactory,
            context,
        )
    }
}

/**
 * Отображает текущий граф навигации в удобной для человека форме.
 */
internal class NavigationGraphUmlDiagramComponent(
    viewModelFactory: NavigationGraphUmlDiagramViewModelFactory,
    context: ComponentContext,
) : ComponentContext by context, ComposeComponent {
    private val viewModel: NavigationGraphUmlDiagramViewModel = instanceKeeper.getOrCreate { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) = NavigationGraphUmlDiagramContent(viewModel, modifier)
}
