package ru.vs.core.collections.tree

/**
 * Расширение стандартного [TreeNode], добавляет связь с родителем.
 *
 * @property parent ссылка на родителя если он существует.
 */
interface LinkedTreeNode<T> : TreeNode<T, LinkedTreeNode<T>> {
    val parent: LinkedTreeNode<T>?
}

class LinkedTreeNodeImpl<T> internal constructor(
    parent: LinkedTreeNodeImpl<T>?,
    override val value: T,
    override val children: Collection<LinkedTreeNodeImpl<T>>,
) : LinkedTreeNode<T> {
    override var parent: LinkedTreeNodeImpl<T>? = parent
        internal set
}

/**
 * DSL для построения [LinkedTreeNode] деревьев.
 */
fun <T> linkedNodeOf(
    value: T,
    children: Collection<LinkedTreeNodeImpl<T>> = emptyList(),
): LinkedTreeNodeImpl<T> {
    return LinkedTreeNodeImpl(null, value, children).apply {
        children.forEach { child -> child.parent = this }
    }
}

/**
 * DSL для построения [LinkedTreeNode] деревьев.
 */
fun <T> linkedNodeOf(
    value: T,
    vararg children: LinkedTreeNodeImpl<T>,
): LinkedTreeNodeImpl<T> = linkedNodeOf(value, children.toList())

/**
 * Итератор по всему дереву (включая ноды выше текущей), сначала итерируется по детям, как это делает обычный
 * [asSequence], потом делает то же самое для родителя, но пропускает уже обойденные ноды.
 */
fun <T> LinkedTreeNode<T>.asSequenceUp(): Sequence<LinkedTreeNode<T>> = sequence {
    var lastNode: LinkedTreeNode<T>? = null
    var currentNode: LinkedTreeNode<T>? = this@asSequenceUp

    while (currentNode != null) {
        yieldAll(currentNode.asSequence { it != lastNode })
        lastNode = currentNode
        currentNode = currentNode.parent
    }
}

/**
 * Возвращает путь от корня дерева до текущей ноды, первым элементом будет корень, последним это нода.
 */
fun <T> LinkedTreeNode<T>.path(): List<LinkedTreeNode<T>> {
    val reversedPath = sequence {
        var node: LinkedTreeNode<T>? = this@path
        while (node != null) {
            yield(node)
            node = node.parent
        }
    }
    return reversedPath.toList().reversed()
}
