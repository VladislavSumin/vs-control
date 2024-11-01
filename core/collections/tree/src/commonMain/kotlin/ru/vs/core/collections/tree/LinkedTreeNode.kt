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
