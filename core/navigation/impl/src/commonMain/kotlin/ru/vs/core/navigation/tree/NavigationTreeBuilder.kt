package ru.vs.core.navigation.tree

import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.repository.NavigationRepository
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.utils.joinToStingFormatted

internal class NavigationTreeBuilder(
    private val repository: NavigationRepository,
) {
    fun build() = NavigationTree(buildNavGraph())

    /**
     * Строит навигационное дерево.
     *
     * @return возвращает корень полученного дерева.
     */
    private fun buildNavGraph(): Node {
        val rootScreen = findRootScreen()
        return buildNode(
            parent = null,
            hostInParent = null,
            screenKey = rootScreen,
        )
    }

    /**
     * Рекурсивная функция которая строит дерево.
     *
     * @param parent родительская нода, нужна для создания дерева с возможностью перемещаться вверх, null для головной
     * ноды дерева.
     * @param hostInParent родительский хост внутри которого находится данная нода.
     * @param screenKey ключ соответствующий [Node] которую нужно создать.
     */
    private fun buildNode(
        parent: Node?,
        hostInParent: NavigationHost?,
        screenKey: ScreenKey<*>,
    ): Node {
        val screenRegistration = repository.screens[screenKey] ?: error("Unreachable")

        val screenInfo = ScreenInfo(
            screenKey = screenKey,
            hostInParent = hostInParent,
            factory = screenRegistration.factory,
            defaultParams = screenRegistration.defaultParams,
            opensIn = screenRegistration.opensIn,
            navigationHosts = screenRegistration.navigationHosts,
            nameForLogs = screenRegistration.nameForLogs,
            description = screenRegistration.description,
        )

        val node = MutableNode(parent, screenInfo)

        // Пробегаемся по всем навигационным хостам объявленным для данной ноды.
        screenRegistration.navigationHosts.forEach { navHost ->
            // Пробегаемся по всем экранам которые могут быть открыты в данном navHost

            repository.screens
                .asSequence()
                .filter { (_, v) -> navHost in v.opensIn }
                .forEach { (k, v) ->
                    val childNode = buildNode(
                        parent = node,
                        hostInParent = navHost,
                        screenKey = k,
                    )
                    node.children.add(childNode)
                    // TODO починить чек
                    // check(oldChildren == null) { "Double children registration" }
                }
        }
        return node
    }

    /**
     * Ищет root screen, этим экраном является такой экран который невозможно открыть из другой точки графа.
     */
    private fun findRootScreen(): ScreenKey<*> {
        // Множество экранов у которых нет точек входа (множество рутовых экранов)
        val roots: Set<ScreenKey<*>> = repository.screens
            .filter { (_, v) -> v.opensIn.isEmpty() }
            .keys

        check(roots.size == 1) {
            val formattedRoots = roots.joinToStingFormatted { repository.screens[it]!!.nameForLogs }
            "Found more than one root or no root found, roots:\n$formattedRoots"
        }
        return roots.first()
    }

    /**
     * Изменяемая версия [Node], необходима из-за двухсторонней связи [parent]/[children], чтобы избежать
     * лишних копирований ноды.
     */
    private class MutableNode(
        override val parent: Node?,
        override val value: ScreenInfo,
        override val children: MutableList<Node> = mutableListOf(),
    ) : Node
}
