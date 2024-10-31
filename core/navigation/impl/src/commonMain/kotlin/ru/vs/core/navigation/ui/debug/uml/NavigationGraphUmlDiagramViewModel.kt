package ru.vs.core.navigation.ui.debug.uml

import ru.vs.core.decompose.ViewModel
import ru.vs.core.navigation.tree.NavigationTree
import ru.vs.core.navigation.tree.Node

internal class NavigationGraphUmlDiagramViewModelFactory(
    private val navigationTreeProvider: () -> NavigationTree,
) {
    fun create(
        navigationTreeInterceptor: (NavigationGraphUmlNode) -> NavigationGraphUmlNode,
    ): NavigationGraphUmlDiagramViewModel {
        return NavigationGraphUmlDiagramViewModel(navigationTreeProvider(), navigationTreeInterceptor)
    }
}

internal class NavigationGraphUmlDiagramViewModel(
    private val navigationTree: NavigationTree,
    private val navigationTreeInterceptor: (NavigationGraphUmlNode) -> NavigationGraphUmlNode,
) : ViewModel() {

    val graph = createDebugGraph()

    private fun createDebugGraph(): NavigationGraphUmlDiagramViewState {
        return NavigationGraphUmlDiagramViewState(
            root = navigationTreeInterceptor(mapNodesRecursively(navigationTree.root)),
        )
    }

    /**
     * Переводит все [NavigationTree.Node] исходного графа навигации в граф [NavigationGraphUmlDiagramViewState.Node].
     */
    private fun mapNodesRecursively(node: Node): NavigationGraphUmlNode {
        return NavigationGraphUmlNode(
            info = NavigationGraphUmlNode.Info(
                name = node.value.nameForLogs,
                hasDefaultParams = node.value.defaultParams != null,
                isPartOfMainGraph = true,
                description = node.value.description,
            ),
            children = node.children.map { mapNodesRecursively(it) },
        )
    }
}
