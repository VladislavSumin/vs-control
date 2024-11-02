package ru.vs.core.navigation.navigator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.serialization.KSerializer
import ru.vs.core.collections.tree.LinkedTreeNode
import ru.vs.core.collections.tree.asSequenceUp
import ru.vs.core.collections.tree.findByPath
import ru.vs.core.collections.tree.path
import ru.vs.core.navigation.Navigation
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

    internal val serializer: KSerializer<ScreenParams>
        get() = navigation.navigationSerializer.serializer

    private val screenNavigators = mutableMapOf<ScreenPath, ScreenNavigator>()

    /**
     * Регистрирует [screenNavigator] с учетом жизненного цикла [ComponentContext].
     */
    fun registerScreenNavigator(screenNavigator: ScreenNavigator, lifecycle: Lifecycle) {
        val oldScreenNavigator = screenNavigators.put(screenNavigator.screenPath, screenNavigator)
        check(oldScreenNavigator == null) {
            "Screen navigator for ${screenNavigator.screenPath} already registered"
        }
        lifecycle.doOnDestroy {
            val navigator = screenNavigators.remove(screenNavigator.screenPath)
            check(navigator != null) { "Screen navigator for ${screenNavigator.screenPath} not found" }
        }
    }

    /**
     * Открывает экран соответствующий переданным [screenParams], при этом поиск пути производится относительно
     * переданного [screenPath]. (подробнее про поиск пути до экрана можно прочитать в документации).
     */
    fun open(screenPath: ScreenPath, screenParams: ScreenParams) {
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
        val destinationPath: List<LinkedTreeNode<ScreenInfo>> = destinationNode.path()

        destinationPath.indices
            .drop(1)
            .forEach { index ->
                // TODO
            }

        // TODO временное решение, нужно пройтись по всем путям.
        val path = navigation.navigationTree.getDestinationsForPath(screenPath, screenParams).first()

        // TODO тоже что то на временном.
        path.path.indices.drop(1).forEach { index ->
            screenNavigators[ScreenPath(path.path.subList(0, index))]!!.openInsideThisScreen(path.path[index])
        }
    }

    fun close(screenPath: ScreenPath, screenParams: ScreenParams) {
        val index = screenPath.indexOfLast { it == screenParams }
        if (index == -1) return
        val path = screenPath.subList(0, index)
        screenNavigators[path]!!.closeInsideThisScreen(screenParams)
    }
}
