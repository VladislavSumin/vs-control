package ru.vs.core.navigation.tree

import com.arkivanov.essenty.statekeeper.polymorphicSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.vs.core.navigation.NavigationHost
import ru.vs.core.navigation.ScreenParams
import ru.vs.core.navigation.repository.DefaultScreenRegistration
import ru.vs.core.navigation.repository.NavigationRepository
import ru.vs.core.navigation.screen.DefaultScreenKey
import ru.vs.core.navigation.screen.ScreenKey
import ru.vs.core.navigation.screen.ScreenPath
import ru.vs.core.utils.joinToStingFormatted
import kotlin.reflect.KClass

/**
 * Главное древо навигации, описывает связи между экранами, то какие экраны открывают внутри себя другие экраны.
 *
 * @param repository репозиторий с исходными данными для построения дерева.
 */
class NavigationTree internal constructor(
    repository: NavigationRepository,
) {

    /**
     * Указатель на вершину дерева навигации.
     */
    internal val root: Node = buildNavGraph(repository)

    /**
     * Сериализатор для всех зарегистрированных [ScreenParams], используется внутри decompose для сохранения и
     * восстановления состояния приложения.
     */
    internal val serializer = polymorphicSerializer(
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
    internal fun getDestinationsForPath(
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

    /**
     * Находит [Node] по переданному [screenPath].
     */
    private fun findNode(screenPath: ScreenPath): Node {
        var node = root
        check(root.screenKey == ScreenKey(screenPath.path.first()::class)) {
            "Screen path root not equals root node, root node = $node, path = $screenPath"
        }
        screenPath.path.asSequence().drop(1).forEach { screenParams ->
            node = node.children[ScreenKey(screenParams::class)]
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
        screenKey: DefaultScreenKey,
        repository: NavigationRepository,
    ): Node {
        val screenRegistration = repository.screens[screenKey] ?: error("Unreachable")
        val node = MutableNode(parent, hostInParent, screenKey, screenRegistration)

        // Пробегаемся по всем навигационным хостам объявленным для данной ноды.
        screenRegistration.navigationHosts.forEach { navHost ->
            // Пробегаемся по всем экранам которые могут быть открыты в данном navHost
            repository.endpoints[navHost]?.forEach { screenKey ->
                // Строим дочерние экраны для таких нод
                val oldChildren = node.children.put(screenKey, buildNode(node, navHost, screenKey, repository))
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
    private fun findRootScreen(repository: NavigationRepository): DefaultScreenKey {
        // Множество экранов у которых нет точек входа (множество рутовых экранов)
        val roots: Set<DefaultScreenKey> = repository.screens.keys - repository.endpoints.values.flatten().toSet()

        check(roots.size == 1) {
            val formattedRoots = roots.joinToStingFormatted { repository.screens[it]!!.nameForLogs }
            "Found more than one root or no root found, roots:\n$formattedRoots"
        }
        return roots.first()
    }

    /**
     * Узел навигационного дерева.
     *
     * @property parent родительская нода, null только для корневой ноды графа.
     * @property hostInParent хост, который занимает данная нода в родительском экране, null для root ноды.
     * @property screenKey ключ экрана за который отвечает эта нода.
     * @property screenRegistration параметры регистрации соответствующего экрана.
     * @property children список дочерних нод (экраны в которые возможна навигация из этой ноды).
     */
    internal interface Node {
        val parent: Node?
        val hostInParent: NavigationHost?
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
        override val hostInParent: NavigationHost?,
        override val screenKey: DefaultScreenKey,
        override val screenRegistration: DefaultScreenRegistration,
        override val children: MutableMap<DefaultScreenKey, Node> = mutableMapOf(),
    ) : Node
}
