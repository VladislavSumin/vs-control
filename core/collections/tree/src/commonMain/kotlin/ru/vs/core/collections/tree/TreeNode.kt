package ru.vs.core.collections.tree

interface TreeNode<T, N : TreeNode<T, N>> {
    val value: T
    val children: Collection<N>
}

/**
 * Итератор по всем [TreeNode.children] начиная с детей и заканчивая внуками и далее.
 */
fun <T, N : TreeNode<T, N>> N.asSequence(): Sequence<N> = sequence {
    val root = this@asSequence
    yield(root)
    var children = root.children
    while (children.isNotEmpty()) {
        val newChildren = mutableListOf<N>()
        children.forEach { node ->
            yield(node)
            newChildren += node.children
        }
        children = newChildren
    }
}

/**
 * Ищет [TreeNode] по переданному пути, начиная от текущей (включительно).
 */
fun <T, K, N : TreeNode<T, N>> N.findByPath(path: List<K>, keySelector: (T) -> K): N? {
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
