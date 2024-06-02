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
            root = generateFakeNavigationNodesFist(),
        )
    }

    /**
     * Не все пути навигации содержаться в графе навигации. Несколько первых экранов туда не попадают, поэтому
     * добавляем их вручную.
     */
    private fun generateFakeNavigationNodesFist(): NavigationGraphUmlDiagramViewState.Node {
        val normalGraph = mapNodesRecursively(navigationTree.root)

        val initializedRootScreenNode = NavigationGraphUmlDiagramViewState.Node(
            info = NavigationGraphUmlDiagramViewState.NodeInfo(
                name = "InitializedRootScreenComponent",
                hasDefaultParams = false,
                isPartOfMainGraph = false,
            ),
            children = listOf(normalGraph),
        )

        val splashScreenNode = NavigationGraphUmlDiagramViewState.Node(
            info = NavigationGraphUmlDiagramViewState.NodeInfo(
                name = "SplashScreenComponent",
                hasDefaultParams = false,
                isPartOfMainGraph = false,
            ),
            children = emptyList(),
        )

        val rootScreenNode = NavigationGraphUmlDiagramViewState.Node(
            info = NavigationGraphUmlDiagramViewState.NodeInfo(
                name = "RootScreenComponent",
                hasDefaultParams = false,
                isPartOfMainGraph = false,
            ),
            children = listOf(
                splashScreenNode,
                initializedRootScreenNode,
            ),
        )

        return rootScreenNode
    }

    private fun mapNodesRecursively(node: NavigationTree.Node): NavigationGraphUmlDiagramViewState.Node {
        return NavigationGraphUmlDiagramViewState.Node(
            info = NavigationGraphUmlDiagramViewState.NodeInfo(
                name = node.screenRegistration.nameForLogs,
                hasDefaultParams = node.screenRegistration.defaultParams != null,
                isPartOfMainGraph = true,
            ),
            children = node.children.map { mapNodesRecursively(it.value) },
        )
    }
}
