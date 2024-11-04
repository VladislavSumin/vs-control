package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.Component
import ru.vs.core.decompose.ComposeComponent

class NavigationGraphUmlDiagramComponentFactory internal constructor(
    private val viewModelFactory: NavigationGraphUmlDiagramViewModelFactory,
) {
    /**
     * @param navigationTreeInterceptor перехватывает ноды созданные из графа навигации и позволяет внести в полученный
     * граф любые изменения, например добавить экраны инициализации которые не являются частью графа. **Внимание** этот
     * параметр передается в viewModel и имеет отличный от компонента lifecycle.
     */
    fun create(
        context: ComponentContext,
        navigationTreeInterceptor: (NavigationGraphUmlNode) -> NavigationGraphUmlNode = { it },
    ): ComposeComponent {
        return NavigationGraphUmlDiagramComponent(
            viewModelFactory,
            context,
            navigationTreeInterceptor,
        )
    }
}

/**
 * Отображает текущий граф навигации в удобной для человека форме.
 */
internal class NavigationGraphUmlDiagramComponent(
    viewModelFactory: NavigationGraphUmlDiagramViewModelFactory,
    context: ComponentContext,
    navigationTreeInterceptor: (NavigationGraphUmlNode) -> NavigationGraphUmlNode,
) : Component(context) {
    private val viewModel: NavigationGraphUmlDiagramViewModel = viewModel {
        viewModelFactory.create(navigationTreeInterceptor)
    }

    @Composable
    override fun Render(modifier: Modifier) = NavigationGraphUmlDiagramContent(viewModel, modifier)
}
