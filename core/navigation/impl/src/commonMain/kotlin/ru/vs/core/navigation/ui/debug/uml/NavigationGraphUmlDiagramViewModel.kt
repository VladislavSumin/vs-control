package ru.vs.core.navigation.ui.debug.uml

import ru.vs.core.decompose.ViewModel
import ru.vs.core.navigation.Navigation
import ru.vs.core.navigation.tree.NavigationTree
import ru.vs.core.navigation.tree.Node

internal class NavigationGraphUmlDiagramViewModelFactory(
    private val navigationProvider: () -> Navigation,
) {
    fun create(
        navigationTreeInterceptor: (NavigationGraphUmlNode) -> NavigationGraphUmlNode,
    ): NavigationGraphUmlDiagramViewModel {
        return NavigationGraphUmlDiagramViewModel(navigationProvider(), navigationTreeInterceptor)
    }
}

internal class NavigationGraphUmlDiagramViewModel(
    private val navigation: Navigation,
    private val navigationTreeInterceptor: (NavigationGraphUmlNode) -> NavigationGraphUmlNode,
) : ViewModel() {

    val graph = createDebugGraph()

    private fun createDebugGraph(): NavigationGraphUmlDiagramViewState {
        return NavigationGraphUmlDiagramViewState(
            root = navigationTreeInterceptor(mapNodesRecursively(navigation.navigationTree.root)),
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
