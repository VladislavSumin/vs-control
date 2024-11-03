package ru.vs.core.collections.tree

class LinkedTreeNodeImpl<T> internal constructor(
    parent: LinkedTreeNodeImpl<T>?,
    override val value: T,
    override val children: Collection<LinkedTreeNodeImpl<T>>,
) : LinkedTreeNode<T> {
    override var parent: LinkedTreeNodeImpl<T>? = parent
        internal set
}

fun <T> linkedNodeOf(
    value: T,
    vararg children: LinkedTreeNodeImpl<T>,
): LinkedTreeNodeImpl<T> {
    val childrenList = children.toList()
    return LinkedTreeNodeImpl(null, value, childrenList).apply {
        childrenList.forEach { child -> child.parent = this }
    }
}
