package ru.vs.core.collections.tree

interface LinkedTree<T> {
    val root: LinkedNode<T>

    interface LinkedNode<T> {
        val value: T
        val parent: LinkedNode<T>?
        val children: List<LinkedNode<T>>
    }
}
