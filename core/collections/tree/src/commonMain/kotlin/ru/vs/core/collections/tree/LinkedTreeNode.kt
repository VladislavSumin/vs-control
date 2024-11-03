package ru.vs.core.collections.tree

interface LinkedTreeNode<T> : TreeNode<T, LinkedTreeNode<T>> {
    val parent: LinkedTreeNode<T>?
}

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
