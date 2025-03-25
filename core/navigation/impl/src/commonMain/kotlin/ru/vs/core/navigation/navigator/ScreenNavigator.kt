package ru.vs.core.navigation.navigator

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.serialization.KSerializer
import ru.vladislavsumin.core.collections.tree.LinkedTreeNode
import ru.vladislavsumin.core.navigation.NavigationHost
import ru.vladislavsumin.core.navigation.ScreenParams
import ru.vs.core.navigation.NavigationLogger
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.navigation.screen.asErasedKey
import ru.vs.core.navigation.tree.ScreenInfo

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
    internal val parentNavigator: ScreenNavigator?,
    internal val screenPath: ScreenPath,
    internal val node: LinkedTreeNode<ScreenInfo>,
    internal val serializer: KSerializer<ScreenParams>,
    private val lifecycle: Lifecycle,
    internal val initialPath: ScreenPath?,
) {
    /**
     * Список зарегистрированных на этом экране [HostNavigator].
     */
    private val navigationHosts = mutableMapOf<NavigationHost, HostNavigator>()

    /**
     * Текущие активные навигаторы среди дочерних экранов.
     */
    private val childScreenNavigators = mutableMapOf<ScreenParams, ScreenNavigator>()

    internal val screenParams = (screenPath.last() as ScreenPath.PathElement.Params).screenParams

    /**
     * Экран в контексте которого существует данный навигатор.
     */
    internal lateinit var screen: Screen

    init {
        // Регистрируем этот навигатор в родительском.
        parentNavigator?.registerScreenNavigator(this, lifecycle)

        lifecycle.doOnCreate {
            // Проверяем что экран действительно зарегистрировал все типы навигации которые может открывать.
            val expectedHosts = node.value.navigationHosts
            val actualHosts = navigationHosts.keys
            check(expectedHosts == actualHosts) {
                "Actual host registration doesn't match expected. Actual:$actualHosts, expected:$expectedHosts"
            }
        }
    }

    /**
     * Возвращает стартовые параметры для [navigationHost] если таковые есть.
     */
    internal fun getInitialParamsFor(navigationHost: NavigationHost): ScreenParams? {
        val element = initialPath?.first() ?: return null
        val screenKey = element.asErasedKey()
        val childNode = node.children.find { it.value.screenKey == screenKey }?.value
            ?: error("Child node with screenKey=$screenKey not found")
        return if (childNode.hostInParent == navigationHost) {
            when (element) {
                is ScreenPath.PathElement.Key -> childNode.defaultParams ?: error("No default params")
                is ScreenPath.PathElement.Params -> element.screenParams
            }
        } else {
            null
        }
    }

    /**
     * Регистрирует [screenNavigator] с учетом жизненного цикла [ComponentContext].
     */
    internal fun registerScreenNavigator(screenNavigator: ScreenNavigator, lifecycle: Lifecycle) {
        val oldScreenNavigator = childScreenNavigators.put(screenNavigator.screenParams, screenNavigator)
        check(oldScreenNavigator == null) {
            "Screen navigator for ${screenNavigator.screenPath} already registered"
        }
        lifecycle.doOnDestroy {
            val navigator = childScreenNavigators.remove(screenNavigator.screenParams)
            check(navigator != null) { "Screen navigator for ${screenNavigator.screenPath} not found" }
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

    internal fun openInsideThisScreen(screenPath: ScreenPath) {
        NavigationLogger.t {
            "ScreenNavigator(screenParams=$screenParams).openInsideThisScreen(screenPath=$screenPath)"
        }
        openInsideThisScreen(screenPath.first())
        val childPath = ScreenPath(screenPath.drop(1))
        if (childPath.isNotEmpty()) {
            val childElement = screenPath.first()
            val childNavigator = when (childElement) {
                is ScreenPath.PathElement.Key -> childScreenNavigators.asSequence()
                    .first { entry -> entry.key.asErasedKey() == childElement.screenKey }.value

                is ScreenPath.PathElement.Params -> childScreenNavigators[childElement.screenParams]
            }
            childNavigator!!.openInsideThisScreen(childPath)
        }
    }

    /**
     * Внутренняя функция предназначенная для использования глобальным навигатором.
     * После нахождения пути к экрану открываемому через вызов [open], навигатор последовательно вызывает эту
     * функцию на **каждом** экране в пути, тем самым переключая состояние на требуемое.
     */
    private fun openInsideThisScreen(screen: ScreenPath.PathElement) {
        val screenKey = screen.asErasedKey()
        val childNode = node.children.find { it.value.screenKey == screenKey }
            ?: error("Child node with screenKey=$screenKey not found")
        val hostNavigator = navigationHosts[childNode.value.hostInParent]
            ?: error("Host navigator for host=${childNode.value.hostInParent} not found")
        when (screen) {
            is ScreenPath.PathElement.Key -> hostNavigator.open(screen.screenKey) { childNode.value.defaultParams!! }
            is ScreenPath.PathElement.Params -> hostNavigator.open(screen.screenParams)
        }
    }

    internal fun closeInsideThisScreen(screenPath: ScreenPath) {
        NavigationLogger.t {
            "ScreenNavigator(screenParams=$screenParams).closeInsideThisScreen(screenPath=$screenPath)"
        }
        if (screenPath.size == 1) {
            closeInsideThisScreen((screenPath.first() as ScreenPath.PathElement.Params).screenParams)
        } else {
            val childElement = screenPath.firstOrNull() ?: return
            val childNavigator = when (childElement) {
                is ScreenPath.PathElement.Key -> childScreenNavigators.asSequence()
                    .first { entry -> entry.key.asErasedKey() == childElement.screenKey }.value

                is ScreenPath.PathElement.Params -> childScreenNavigators[childElement.screenParams]
            }
            childNavigator?.closeInsideThisScreen(ScreenPath(screenPath.drop(1)))
        }
    }

    private fun closeInsideThisScreen(screenParams: ScreenParams) {
        // TODO убрать дублирование кода.
        val screenKey = ScreenKey(screenParams::class)
        val childNode = node.children.find { it.value.screenKey == screenKey }
            ?: error("Child node with screenKey=$screenKey not found")
        val hostNavigator = navigationHosts[childNode.value.hostInParent]
            ?: error("Host navigator for host=${childNode.value.hostInParent} not found")
        hostNavigator.close(screenParams)
    }

    /**
     * Возвращает фабрику для создания дочернего экрана.
     */
    @Suppress("UNCHECKED_CAST")
    internal fun getChildScreenFactory(screenKey: ScreenKey<ScreenParams>): ScreenFactory<ScreenParams, *> {
        return node.children.find { it.value.screenKey == screenKey }!!.value.factory as ScreenFactory<ScreenParams, *>
    }

    /**
     * Запрашивает задержку splash экрана, сначала для текущего экрана и только потом для всех дочерних навигаторов.
     */
    internal suspend fun delaySplashScreen() {
        screen.delaySplashScreenInternal()
        childScreenNavigators.values.forEach { it.delaySplashScreen() }
    }

    /**
     * Открывает экран соответствующий переданным [screenParams], при этом, при поиске места открытия экрана учитывается
     * текущее место. (подробнее про приоритет выбора места написано в документации).
     */
    fun open(screenParams: ScreenParams) = globalNavigator.open(screenPath, screenParams)
    fun close(screenParams: ScreenParams) = globalNavigator.close(screenPath, screenParams)
    fun close() = globalNavigator.close(screenPath, (screenPath.last() as ScreenPath.PathElement.Params).screenParams)
}
