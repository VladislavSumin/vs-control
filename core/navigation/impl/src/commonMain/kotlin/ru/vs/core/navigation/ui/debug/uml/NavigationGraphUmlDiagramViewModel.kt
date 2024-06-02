package ru.vs.core.navigation.ui.debug.uml

import ru.vs.core.decompose.ViewModel
import ru.vs.core.navigation.tree.NavigationTree

internal class NavigationGraphUmlDiagramViewModelFactory(
    private val navigationTreeProvider: () -> NavigationTree,
) {
    fun create(): NavigationGraphUmlDiagramViewModel {
        return NavigationGraphUmlDiagramViewModel(navigationTreeProvider())
    }
}

internal class NavigationGraphUmlDiagramViewModel(
    private val navigationTree: NavigationTree,
) : ViewModel() {

    val graph = createDebugGraph()

    private fun createDebugGraph(): NavigationGraphUmlDiagramViewState {
        return NavigationGraphUmlDiagramViewState(
            root = MutableChildren(
                navigationTree.root.screenRegistration.nameForLogs,
            ),
        )
    }

    private data class MutableChildren(
        override val name: String,
        override val children: MutableList<NavigationGraphUmlDiagramViewState.Element> = mutableListOf(),
    ) : NavigationGraphUmlDiagramViewState.Element
}
