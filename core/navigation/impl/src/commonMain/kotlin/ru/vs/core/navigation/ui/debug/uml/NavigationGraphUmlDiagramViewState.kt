package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.runtime.Stable

@Stable
internal data class NavigationGraphUmlDiagramViewState(
    val root: Node,
) {

    @Stable
    data class Node(
        val name: String,
        val children: List<Node>,
    )
}
