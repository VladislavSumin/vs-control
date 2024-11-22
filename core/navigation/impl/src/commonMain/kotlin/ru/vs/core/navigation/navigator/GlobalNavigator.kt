package ru.vs.core.navigation.navigator

import ru.vs.core.collections.tree.LinkedTreeNode
import ru.vs.core.collections.tree.asSequenceUp
import ru.vs.core.collections.tree.findByPath
import ru.vs.core.collections.tree.path
import ru.vs.core.navigation.Navigation
import ru.vs.core.navigation.NavigationLogger
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.navigation.screen.asErasedKey
import ru.vs.core.navigation.tree.ScreenInfo

/**
 * Глобальный навигатор.
 */
internal class GlobalNavigator(
    private val navigation: Navigation,
) {

    internal lateinit var rootNavigator: ScreenNavigator

    /**
     * Открывает экран соответствующий переданным [screenParams], при этом поиск пути производится относительно
     * переданного [screenPath]. (подробнее про поиск пути до экрана можно прочитать в документации).
     */
    fun open(screenPath: ScreenPath, screenParams: ScreenParams) {
        NavigationLogger.i { "Open screen ${screenParams::class.simpleName}" }
        val path = createOpenPath(screenPath, screenParams)
        rootNavigator.openInsideThisScreen(path)
    }

    internal fun createOpenPath(screenPath: ScreenPath, screenParams: ScreenParams): ScreenPath {
        val screenKey = screenParams.asErasedKey()

        // Нода в графе навигации соответствующая переданному пути.
        val fromScreenNode: LinkedTreeNode<ScreenInfo> = navigation.navigationTree.findByPath(
            path = screenPath.map { it.asErasedKey() },
            keySelector = { it.screenKey },
        )!!

        // Нода в графе навигации куда мы хотим перейти.
        val destinationNode: LinkedTreeNode<ScreenInfo> = fromScreenNode
            .asSequenceUp()
            .first { node -> node.value.screenKey == screenKey }

        // Путь до искомой ноды.
        val destinationKeysPath: List<ScreenPath.PathElement.Key> = destinationNode.path()
            .map { node -> node.value.screenKey }
            .map { ScreenPath.PathElement.Key(it) }
        return ScreenPath(destinationKeysPath.drop(1).dropLast(1) + ScreenPath.PathElement.Params(screenParams))
    }

    fun close(screenPath: ScreenPath, screenParams: ScreenParams) {
        NavigationLogger.i { "Close screen ${screenParams::class.simpleName}" }
        val index = screenPath.indexOfLast { it == ScreenPath.PathElement.Params(screenParams) }
        if (index == -1) return
        val path = screenPath.subList(0, index)
        rootNavigator.closeInsideThisScreen(ScreenPath(path.drop(1) + ScreenPath.PathElement.Params(screenParams)))
    }
}
