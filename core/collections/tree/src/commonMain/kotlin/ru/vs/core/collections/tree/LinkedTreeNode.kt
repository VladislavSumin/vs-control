package ru.vs.core.collections.tree

import kotlin.collections.find

interface LinkedTreeNode<T> {
    val value: T
    val parent: LinkedTreeNode<T>?
    val children: List<LinkedTreeNode<T>>
}

/**
 * Итератор по всем [LinkedTreeNode.children] начиная с детей и заканчивая внуками и далее.
 */
fun <T> LinkedTreeNode<T>.asSequence(): Sequence<LinkedTreeNode<T>> = sequence {
    val root = this@asSequence
    yield(root)
    var children = root.children
    while (children.isNotEmpty()) {
        val newChildren = mutableListOf<LinkedTreeNode<T>>()
        children.forEach { node ->
            yield(node)
            newChildren += node.children
        }
        children = newChildren
    }
}

/**
 * Итератор по всему дереву (включая ноды выше текущей), сначала итерируется по детям, как это делает обычный
 * [asSequence], потом делает то же самое для родителя, но пропускает уже обойденные ноды.
 */
fun <T> LinkedTreeNode<T>.asSequenceUp(): Sequence<LinkedTreeNode<T>> = sequence {
    var lastNode: LinkedTreeNode<T>? = null
    var currentNode: LinkedTreeNode<T>? = this@asSequenceUp

    while (currentNode != null) {
        yieldAll(
            currentNode
                .asSequence()
                .filter { it != lastNode },
        )
        lastNode = currentNode
        currentNode = currentNode.parent
    }
}

/**
 * Ищет [LinkedTreeNode] по переданному пути, начиная от текущей (включительно).
 */
fun <T, K> LinkedTreeNode<T>.findByPath(path: List<K>, keySelector: (T) -> K): LinkedTreeNode<T>? {
    // Если путь пустой, то нечего искать.
    if (path.isEmpty()) return null

    // Проверяем что путь действительно начинается от корня.
    if (path.first() != keySelector(value)) return null

    // Если путь длинной один, то искомая нода текущая.
    if (path.size == 1) return this

    var node = this
    path
        .asSequence()
        .drop(1) // Пропускаем корень, который проверили ранее.
        .forEach { element ->
            node = node.children.find { node -> keySelector(node.value) == element } ?: return null
        }

    return node
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
