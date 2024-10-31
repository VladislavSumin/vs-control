package ru.vs.core.navigation.tree

import com.arkivanov.essenty.statekeeper.ExperimentalStateKeeperApi
import com.arkivanov.essenty.statekeeper.polymorphicSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.vs.core.collections.tree.LinkedTree
import ru.vs.core.collections.tree.LinkedTree.LinkedNode
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.repository.NavigationRepository
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.utils.joinToStingFormatted
import kotlin.reflect.KClass

/**
 * Главное древо навигации, описывает связи между экранами, то какие экраны открывают внутри себя другие экраны.
 *
 * @param repository репозиторий с исходными данными для построения дерева.
 */

internal typealias Node = LinkedNode<ScreenInfo>

internal class NavigationTree(
    repository: NavigationRepository,
) : LinkedTree<ScreenInfo> {

    /**
     * Указатель на вершину дерева навигации.
     */
    override val root: Node = buildNavGraph(repository)

    /**
     * Сериализатор для всех зарегистрированных [ScreenParams], используется внутри decompose для сохранения и
     * восстановления состояния приложения.
     */
    @OptIn(ExperimentalSerializationApi::class, ExperimentalStateKeeperApi::class)
    val serializer = polymorphicSerializer(
        ScreenParams::class,
        SerializersModule {
            polymorphic(ScreenParams::class) {
                repository.serializers.forEach { (clazz, serializer) ->
                    subclass(clazz.key as KClass<ScreenParams>, serializer as KSerializer<ScreenParams>)
                }
            }
        },
    )

    /**
     * TODO доку.
     * TODO тут пока просто на скорую руку всякий мусор накидан.
     */
    fun getDestinationsForPath(
        startPath: ScreenPath,
        screenParams: ScreenParams,
    ): Sequence<ScreenPath> = sequence {
        // TODO парента временно берем пока поиска нет
        val startNode = findNode(startPath).parent!!

        // TODO реализовать полный поиск.

        // TODO это пока демо решение, естественно это будет переписано.
        startNode.children.find { it.value.screenKey == ScreenKey(screenParams::class) }!!.value.screenKey
        val path = startPath.path.dropLast(1) + screenParams
        yield(ScreenPath(path))
    }

    /**
     * Находит [Node] по переданному [screenPath].
     */
    private fun findNode(screenPath: ScreenPath): Node {
        var node = root
        check(root.value.screenKey == ScreenKey(screenPath.path.first()::class)) {
            "Screen path root not equals root node, root node = $node, path = $screenPath"
        }
        screenPath.path.asSequence().drop(1).forEach { screenParams ->
            node = node.children.find { it.value.screenKey == ScreenKey(screenParams::class) }
                ?: error("incorrect path, path = $screenPath, lastFoundNode = $node")
        }
        return node
    }

    /**
     * Строит навигационное дерево.
     *
     * @return возвращает головную [Node] полученного дерева.
     */
    private fun buildNavGraph(repository: NavigationRepository): Node {
        val rootScreen = findRootScreen(repository)
        return buildNode(
            parent = null,
            hostInParent = null,
            screenKey = rootScreen,
            repository = repository,
        )
    }

    /**
     * Рекурсивная функция которая строит дерево.
     *
     * @param parent родительская нода, нужна для создания дерева с возможностью перемещаться вверх, null для головной
     * ноды дерева.
     * @param hostInParent родительский хост внутри которого находится данная нода.
     * @param screenKey ключ соответствующий [Node] которую нужно создать.
     * @param repository ссылка на репозиторий всех экранов для поиска нод.
     */
    private fun buildNode(
        parent: Node?,
        hostInParent: NavigationHost?,
        screenKey: ScreenKey<*>,
        repository: NavigationRepository,
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
                        repository = repository,
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
    private fun findRootScreen(repository: NavigationRepository): ScreenKey<*> {
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
