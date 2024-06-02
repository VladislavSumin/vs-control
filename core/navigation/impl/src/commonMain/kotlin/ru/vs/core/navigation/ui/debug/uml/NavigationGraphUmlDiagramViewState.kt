package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.runtime.Stable

@Stable
internal data class NavigationGraphUmlDiagramViewState(
    val root: Node,
) {

    @Stable
    data class Node(
        val info: NodeInfo,
        val children: List<Node>,
    )

    data class NodeInfo(
        val name: String,
        val hasDefaultParams: Boolean,
        val isPartOfMainGraph: Boolean,
    )
}
