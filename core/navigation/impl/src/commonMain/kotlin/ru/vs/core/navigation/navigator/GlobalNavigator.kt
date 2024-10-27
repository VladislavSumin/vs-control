package ru.vs.core.navigation.navigator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.serialization.KSerializer
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.navigation.tree.NavigationTree

/**
 * Глобальный навигатор.
 */
internal class GlobalNavigator(
    private val navigationTree: NavigationTree,
) {

    internal val serializer: KSerializer<ScreenParams>
        get() = navigationTree.serializer

    private val screenNavigators = mutableMapOf<ScreenPath, ScreenNavigator>()

    /**
     * Регистрирует [screenNavigator] с учетом жизненного цикла [ComponentContext].
     */
    context(ComponentContext)
    fun registerScreenNavigator(screenNavigator: ScreenNavigator) {
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
        // TODO временное решение, нужно пройтись по всем путям.
        val path = navigationTree.getDestinationsForPath(screenPath, screenParams).first()

        // TODO тоже что то на временном.
        path.path.indices.drop(1).forEach { index ->
            screenNavigators[ScreenPath(path.path.subList(0, index))]!!.openInsideThisScreen(path.path[index])
        }
    }
}
