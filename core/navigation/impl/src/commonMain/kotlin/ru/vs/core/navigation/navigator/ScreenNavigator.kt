package ru.vs.core.navigation.navigator

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.navigation.tree.Node

/**
 * Навигатор уровня экрана.
 *
 * @param globalNavigator ссылка на global навигатор.
 * @param screenPath путь до экрана соответствующего данному навигатору.
 * @param node нода соответствующая этому экрану в графе навигации.
 * @param lifecycle жизненный цикл компонента к которому привязан этот навигатор.
 */
class ScreenNavigator internal constructor(
    internal val globalNavigator: GlobalNavigator,
    internal val screenPath: ScreenPath,
    internal val node: Node,
    private val lifecycle: Lifecycle,
) {
    /**
     * Список зарегистрированных на этом экране [HostNavigator].
     */
    private val navigationHosts = mutableMapOf<NavigationHost, HostNavigator>()

    init {
        // Регистрируем этот навигатор в глобальном.
        globalNavigator.registerScreenNavigator(this, lifecycle)

        lifecycle.doOnCreate {
            // Проверяем что экран действительно зарегистрировал все типы навигации которые может открывать.
            val expectedHosts = node.children.map { it.value.hostInParent }.toSet()
            val actualHosts = navigationHosts.keys
            check(expectedHosts == actualHosts) {
                "Actual host registration doesn't match expected. Actual:$actualHosts, expected:$expectedHosts"
            }
        }
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
     * Внутренняя функция предназначенная для использования глобальным навигатором.
     * После нахождения пути к экрану открываемому через вызов [open], навигатор последовательно вызывает эту
     * функцию на **каждом** экране в пути, тем самым переключая состояние на требуемое.
     */
    internal fun openInsideThisScreen(screenParams: ScreenParams) {
        val screenKey = ScreenKey(screenParams::class)
        val childNode = node.children.find { it.value.screenKey == screenKey }
            ?: error("Child node with screenKey=$screenKey not found")
        val hostNavigator = navigationHosts[childNode.value.hostInParent]
            ?: error("Host navigator for host=${childNode.value.hostInParent} not found")
        hostNavigator.open(screenParams)
    }

    /**
     * Возвращает фабрику для создания дочернего экрана.
     */
    @Suppress("UNCHECKED_CAST")
    internal fun getChildScreenFactory(screenKey: ScreenKey<ScreenParams>): ScreenFactory<ScreenParams, *> {
        return node.children.find { it.value.screenKey == screenKey }!!.value.factory as ScreenFactory<ScreenParams, *>
    }

    /**
     * Открывает экран соответствующий переданным [screenParams], при этом, при поиске места открытия экрана учитывается
     * текущее место. (подробнее про приоритет выбора места написано в документации).
     */
    fun open(screenParams: ScreenParams) {
        globalNavigator.open(screenPath, screenParams)
    }
}
