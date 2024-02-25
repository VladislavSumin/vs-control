package ru.vs.core.navigation.graph

import ru.vs.core.navigation.NavigationRepository
import ru.vs.core.navigation.screen.DefaultScreenKey

internal class NavigationTree(
    private val repository: NavigationRepository,
) {

    private val root = buildNavGraph()

    val rootScreenKey = root.screenKey

    private fun buildNavGraph(): Node {
        val rootScreen = findRootScreen()
        return buildNode(parent = null, rootScreen)
    }

    private fun buildNode(parent: Node?, screenKey: DefaultScreenKey): Node {
        val node = MutableNode(parent, screenKey)
        repository.navigationHosts[screenKey]?.forEach { navHost ->
            repository.endpoints[navHost]?.forEach { screenKey ->
                node.children[screenKey] = buildNode(node, screenKey)
            }
        }
        return node
    }

    /**
     * Ищет root screen, этим экраном является такой экран который невозможно открыть из другой точки графа.
     */
    private fun findRootScreen(): DefaultScreenKey {
        val roots = repository.screenFactories.keys - repository.endpoints.values.flatten().toSet()
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
     * @property children список дочерних нод (экраны в которые возможна навигация из этой ноды).
     */
    private interface Node {
        val parent: Node?
        val screenKey: DefaultScreenKey
        val children: Map<DefaultScreenKey, Node>
    }

    private class MutableNode(
        override val parent: Node?,
        override val screenKey: DefaultScreenKey,
        override val children: MutableMap<DefaultScreenKey, Node> = mutableMapOf()
    ) : Node
}
