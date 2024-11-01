package ru.vs.core.collections.tree

import ru.vs.core.collections.tree.LinkedTree.LinkedNode
import kotlin.sequences.forEach

/**
 * Возвращает [LinkedNode] по переданному пути.
 */
operator fun <T, K> LinkedTree<T>.get(path: TreePath<T, K>): LinkedNode<T>? {
    // Если путь пустой, то нечего искать.
    if (path.isEmpty()) return null

    // Проверяем что путь действительно начинается от корня.
    if (!path.keyMatcher(root.value, path.first())) return null

    var node = root
    path
        .asSequence()
        .drop(1) // Пропускаем корень, который проверили ранее.
        .forEach { element ->
            node = node.children.find { node -> path.keyMatcher(node.value, element) } ?: return null
        }

    return node
}

/**
 * Ищет [LinkedNode] по заданному [key].
 *
 * Поиск начинается от [startPath] (включительно), сначала по ближайшим детям, потом поиск идет вглубь до конца дерева,
 * после поиск начинается от родителя [startPath], таким образом поиск доходит до вершины, обходя весь граф.
 */
// fun <T, K> LinkedTree<T>.find(startPath: TreePath<T, K>, key: K): Sequence<LinkedNode<T>>? {
//    TODO()
// }

/**
 * Ищет [LinkedNode] по заданному [key].
 *
 * Поиск начинается от [startPath] (включительно), сначала по ближайшим детям, потом поиск идет вглубь до конца дерева.
 * Поиск по вышестоящим листьям не происходит.
 */
fun <T, K> LinkedTree<T>.findChild(startPath: TreePath<T, K>, key: K): Sequence<LinkedNode<T>> {
    val startNode = get(startPath) ?: return emptySequence()
    val keyMatcher = startPath.keyMatcher
    return sequence {
        if (keyMatcher(startNode.value, key)) yield(startNode)
        var children = startNode.children
        while (children.isNotEmpty()) {
            var newChildren = mutableListOf<LinkedNode<T>>()
            children.forEach {
                if (keyMatcher(it.value, key)) yield(it)
                newChildren += it.children
            }
            children = newChildren
        }
    }
}

/**
 * Ищет лист соответствующий [predicate].
 * TODO вынести код в общий итератор.
 */
fun <T> LinkedTree<T>.find(predicate: (T) -> Boolean): T? {
    if (predicate(root.value)) return root.value
    var children = root.children
    while (children.isNotEmpty()) {
        var newChildren = mutableListOf<LinkedNode<T>>()
        children.forEach {
            if (predicate(it.value)) return it.value
            newChildren += it.children
        }
        children = newChildren
    }
    return null
}
