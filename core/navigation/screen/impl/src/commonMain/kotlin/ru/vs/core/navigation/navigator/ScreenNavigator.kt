package ru.vs.core.navigation.navigator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.navigation.tree.NavigationTree

/**
 * Навигатор уровня экрана.
 *
 * @param globalNavigator ссылка на глобальный навигатор.
 * @param screenPath путь до экрана соответствующего данному навигатору.
 * @param node нода соответствующая [screenPath] в графе навигации.
 */
context(ComponentContext)
class ScreenNavigator internal constructor(
    internal val globalNavigator: GlobalNavigator,
    internal val screenPath: ScreenPath,
    internal val node: NavigationTree.Node,
) {
    /**
     * Список зарегистрированных на этом экране [HostNavigator].
     */
    private val navigationHosts = mutableMapOf<NavigationHost, HostNavigator>()

    init {
        globalNavigator.registerScreenNavigator(this)
    }

    /**
     * Регистрирует [HostNavigator] для [NavigationHost] навигации. Учитывает lifecycle [ScreenContext].
     */
    internal fun registerHostNavigator(navigationHost: NavigationHost, hostNavigator: HostNavigator) {
        val oldHost = navigationHosts.put(navigationHost, hostNavigator)
        check(oldHost == null) { "Navigation host $navigationHost already registered" }
        lifecycle.doOnDestroy {
            val host = navigationHosts.remove(navigationHost)
            check(host != null) { "Navigation host $navigationHost not found" }
        }
    }

    /**
     * TODO доку
     */
    internal fun openInside(screenParams: ScreenParams) {
        val screenKey = ScreenKey(screenParams::class)
        val childNode = node.children[screenKey] ?: error("Child node with screenKey=$screenKey not found")
        val hostNavigator = navigationHosts[childNode.hostInParent]
            ?: error("Host navigator for host=${childNode.hostInParent} not found")
        hostNavigator.open(screenParams)
    }

    /**
     * Открывает экран соответствующий переданным [screenParams], при этом, при поиске места открытия экрана учитывается
     * текущее место. (подробнее про приоритет выбора места написано в документации).
     */
    fun open(screenParams: ScreenParams) {
        globalNavigator.open(screenPath, screenParams)
    }
}
