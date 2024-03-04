package ru.vs.core.navigation.graph

import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.repository.DefaultScreenRegistration
import ru.vs.core.navigation.repository.NavigationRepository
import ru.vs.core.navigation.screen.DefaultScreenKey
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath

internal class NavigationTree(
    private val repository: NavigationRepository,
) {

    private val root = buildNavGraph()

    val rootScreenRegistration = root.screenRegistration

    /**
     * TODO доку.
     */
    fun getDestinationsForPath(
        startPath: ScreenPath,
        screenParams: ScreenParams,
    ): Sequence<ScreenPath> = sequence {
        // TODO парента временно берем пока поиска нет
        val startNode = findNode(startPath).parent!!

        // TODO реализовать полный поиск.

        // TODO это пока демо решение, естественно это будет переписано.
        startNode.children[ScreenKey(screenParams::class)]!!.screenKey
        val path = startPath.path.dropLast(1) + screenParams
        yield(ScreenPath(path))
    }

    private fun findNode(screenPath: ScreenPath): Node {
        var node = root
        check(root.screenKey == ScreenKey(screenPath.path.first()::class))
        screenPath.path.asSequence().drop(1).forEach { screenParams ->
            // TODO обработать ошибки.
            node = node.children[ScreenKey(screenParams::class)]!!
        }
        return node
    }

    /**
     * Строит навигационное дерево.
     *
     * @return возвращает головную [Node] полученного дерева.
     */
    private fun buildNavGraph(): Node {
        val rootScreen = findRootScreen()
        return buildNode(parent = null, rootScreen)
    }

    /**
     * Рекурсивная функция которая строит дерево.
     *
     * @param parent родительская нода, нужна для создания дерева с возможностью перемещаться вверх, null для головной
     * ноды дерева.
     * @param screenKey ключ соответствующий [Node] которую нужно создать.
     */
    private fun buildNode(parent: Node?, screenKey: DefaultScreenKey): Node {
        val screenRegistration = repository.screens[screenKey] ?: error("Unreachable")
        val node = MutableNode(parent, screenKey, screenRegistration)

        // Пробегаемся по всем навигационным хостам объявленным для данной ноды.
        screenRegistration.navigationHosts.forEach { navHost ->
            // Пробегаемся по всем экранам которые могут быть открыты в данном navHost
            repository.endpoints[navHost]?.forEach { screenKey ->
                // Строим дочерние экраны для таких нод
                val oldChildren = node.children.put(screenKey, buildNode(node, screenKey))
                // Может возникнуть ситуация когда один screenKey зарегистрирован для двух и более navHost которые
                // зарегистрированы для обработки текущим экраном, эта ситуация недопустима, так как не ясно в каком
                // navHost открывать экран.
                check(oldChildren == null) { "Double children registration" }
            }
        }
        return node
    }

    /**
     * Ищет root screen, этим экраном является такой экран который невозможно открыть из другой точки графа.
     */
    private fun findRootScreen(): DefaultScreenKey {
        val roots = repository.screens.keys - repository.endpoints.values.flatten().toSet()
        check(roots.size == 1) {
            val formatedRoots = roots.joinToString(separator = ",\n") {
                it.key.qualifiedName ?: "NO_NAME"
            }
            "Found more than one root, roots:\n$formatedRoots"
        }
        return roots.first()
    }

    /**
     * Узел навигационного дерева.
     *
     * @property parent родительская нода, null только для корневой ноды графа.
     * @property screenKey ключ экрана за который отвечает эта нода.
     * @property screenRegistration параметры регистрации соответствующего экрана.
     * @property children список дочерних нод (экраны в которые возможна навигация из этой ноды).
     */
    private interface Node {
        val parent: Node?
        val screenKey: DefaultScreenKey
        val screenRegistration: DefaultScreenRegistration
        val children: Map<DefaultScreenKey, Node>
    }

    /**
     * Изменяемая версия [Node], необходима из-за двухсторонней связи [parent]/[children], чтобы избежать
     * лишних копирований ноды.
     */
    private class MutableNode(
        override val parent: Node?,
        override val screenKey: DefaultScreenKey,
        override val screenRegistration: DefaultScreenRegistration,
        override val children: MutableMap<DefaultScreenKey, Node> = mutableMapOf()
    ) : Node
}
