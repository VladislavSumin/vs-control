package ru.vs.core.navigation.ui.debug.uml

import androidx.compose.runtime.Stable

@Stable
internal data class NavigationGraphUmlDiagramViewState(
    val root: Element,
) {

    @Stable
    interface Element {
        val name: String
        val children: List<Element>
    }
}
