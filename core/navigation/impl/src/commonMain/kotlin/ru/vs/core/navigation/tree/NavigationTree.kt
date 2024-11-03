package ru.vs.core.navigation.tree

import ru.vs.core.collections.tree.LinkedTreeNode

/**
 * Главное древо навигации, описывает связи между экранами, то какие экраны открывают внутри себя другие экраны.
 *
 * @param repository репозиторий с исходными данными для построения дерева.
 */

internal class NavigationTree(root: LinkedTreeNode<ScreenInfo>) : LinkedTreeNode<ScreenInfo> by root
