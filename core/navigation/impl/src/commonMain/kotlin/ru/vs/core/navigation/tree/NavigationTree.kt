package ru.vs.core.navigation.tree

import ru.vs.core.collections.tree.LinkedTreeNode
import ru.vs.core.collections.tree.findByPath
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.navigation.screen.asErasedKey

/**
 * Главное древо навигации, описывает связи между экранами, то какие экраны открывают внутри себя другие экраны.
 *
 * @param repository репозиторий с исходными данными для построения дерева.
 */

internal class NavigationTree(
    root: LinkedTreeNode<ScreenInfo>,
) : LinkedTreeNode<ScreenInfo> by root {

    /**
     * TODO доку.
     * TODO тут пока просто на скорую руку всякий мусор накидан.
     */
    fun getDestinationsForPath(
        startPath: ScreenPath,
        screenParams: ScreenParams,
    ): Sequence<ScreenPath> = sequence {
        // TODO парента временно берем пока поиска нет
        val startNode = this@NavigationTree.findByPath(startPath.parent().map { it.asErasedKey() }) { it.screenKey }!!

        // TODO реализовать полный поиск.

        // TODO это пока демо решение, естественно это будет переписано.
        startNode.children.find { it.value.screenKey == ScreenKey(screenParams::class) }!!.value.screenKey
        val path = startPath.path.dropLast(1) + ScreenPath.PathElement.Params(screenParams)
        yield(ScreenPath(path))
    }
}
