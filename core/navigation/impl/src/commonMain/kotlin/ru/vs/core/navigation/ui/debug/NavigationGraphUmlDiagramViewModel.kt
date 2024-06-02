package ru.vs.core.navigation.ui.debug

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
    /**
     * Временная штука протестировать отображение.
     */
    val test = navigationTree.root
}
