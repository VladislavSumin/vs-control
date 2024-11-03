package ru.vs.core.collections.tree

class TreeNodeImpl<T> internal constructor(
    override val value: T,
    override val children: Collection<TreeNodeImpl<T>>,
) : TreeNode<T, TreeNodeImpl<T>>

fun <T> nodeOf(
    value: T,
    vararg children: TreeNodeImpl<T>,
): TreeNodeImpl<T> = TreeNodeImpl(value, children.toList())
