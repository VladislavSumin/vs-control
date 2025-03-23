package ru.vs.core.navigation.ui.debug.uml

import ru.vladislavsumin.core.collections.tree.LinkedTreeNode
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vs.core.navigation.Navigation
import ru.vs.core.navigation.tree.NavigationTree
import ru.vs.core.navigation.tree.ScreenInfo

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
            root = navigationTreeInterceptor(mapNodesRecursively(navigation.navigationTree)),
        )
    }

    /**
     * Переводит все [NavigationTree.Node] исходного графа навигации в граф [NavigationGraphUmlDiagramViewState.Node].
     */
    private fun mapNodesRecursively(node: LinkedTreeNode<ScreenInfo>): NavigationGraphUmlNode {
        return NavigationGraphUmlNode(
            value = NavigationGraphUmlNode.Info(
                name = node.value.screenKey.key.simpleName!!,
                hasDefaultParams = node.value.defaultParams != null,
                isPartOfMainGraph = true,
                description = node.value.description,
            ),
            children = node.children.map { mapNodesRecursively(it) },
        )
    }
}
