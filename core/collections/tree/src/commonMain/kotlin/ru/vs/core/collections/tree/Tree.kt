package ru.vs.core.collections.tree

interface Tree<T> {
    val root: Node<T>

    interface Node<T> {
        val value: T
        val children: List<Node<T>>
    }
}
