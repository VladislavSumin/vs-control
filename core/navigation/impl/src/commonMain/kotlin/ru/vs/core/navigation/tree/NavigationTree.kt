package ru.vs.core.navigation.tree

import ru.vs.core.collections.tree.LinkedTree
import ru.vs.core.collections.tree.LinkedTree.LinkedNode
import ru.vs.core.collections.tree.get
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath

/**
 * Главное древо навигации, описывает связи между экранами, то какие экраны открывают внутри себя другие экраны.
 *
 * @param repository репозиторий с исходными данными для построения дерева.
 */

internal typealias Node = LinkedNode<ScreenInfo>

internal class NavigationTree(
    override val root: LinkedNode<ScreenInfo>,
) : LinkedTree<ScreenInfo> {

    /**
     * TODO доку.
     * TODO тут пока просто на скорую руку всякий мусор накидан.
     */
    fun getDestinationsForPath(
        startPath: ScreenPath,
        screenParams: ScreenParams,
    ): Sequence<ScreenPath> = sequence {
        // TODO парента временно берем пока поиска нет
        val startNode = get(startPath.parent())!!

        // TODO реализовать полный поиск.

        // TODO это пока демо решение, естественно это будет переписано.
        startNode.children.find { it.value.screenKey == ScreenKey(screenParams::class) }!!.value.screenKey
        val path = startPath.path.dropLast(1) + screenParams
        yield(ScreenPath(path))
    }
}
