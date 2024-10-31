package ru.vs.core.collections.tree

interface LinkedTree<T> : Tree<T> {
    override val root: LinkedNode<T>

    interface LinkedNode<T> : Tree.Node<T> {
        val parent: LinkedNode<T>?
        override val children: List<LinkedNode<T>>
    }
}
