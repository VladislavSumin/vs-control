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
            root = mapNodesRecursively(navigationTree.root),
        )
    }

    private fun mapNodesRecursively(node: NavigationTree.Node): NavigationGraphUmlDiagramViewState.Node {
        return NavigationGraphUmlDiagramViewState.Node(
            node.screenRegistration.nameForLogs,
            node.children.map { mapNodesRecursively(it.value) },
        )
    }
}
