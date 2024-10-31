package ru.vs.core.navigation.tree

import ru.vs.core.collections.tree.LinkedTree
import ru.vs.core.collections.tree.LinkedTree.LinkedNode
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
        val startNode = findNode(startPath).parent!!

        // TODO реализовать полный поиск.

        // TODO это пока демо решение, естественно это будет переписано.
        startNode.children.find { it.value.screenKey == ScreenKey(screenParams::class) }!!.value.screenKey
        val path = startPath.path.dropLast(1) + screenParams
        yield(ScreenPath(path))
    }

    /**
     * Находит [Node] по переданному [screenPath].
     */
    private fun findNode(screenPath: ScreenPath): Node {
        var node = root
        check(root.value.screenKey == ScreenKey(screenPath.path.first()::class)) {
            "Screen path root not equals root node, root node = $node, path = $screenPath"
        }
        screenPath.path.asSequence().drop(1).forEach { screenParams ->
            node = node.children.find { it.value.screenKey == ScreenKey(screenParams::class) }
                ?: error("incorrect path, path = $screenPath, lastFoundNode = $node")
        }
        return node
    }
}
