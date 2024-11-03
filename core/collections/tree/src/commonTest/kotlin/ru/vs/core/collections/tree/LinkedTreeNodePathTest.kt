package ru.vs.core.collections.tree

import kotlin.test.Test
import kotlin.test.assertEquals

class LinkedTreeNodePathTest {
    @Test
    fun singleNodePathTest() {
        val node = linkedNodeOf("a")
        val path = node.path()
        assertEquals(1, path.size)
        assertEquals("a", path.first().value)
    }

    @Test
    fun nodePathTest() {
        val childNode: LinkedTreeNode<String>
        linkedNodeOf(
            "a",
            linkedNodeOf(
                "aa",
                linkedNodeOf("aaa"),
                linkedNodeOf("aab").also { childNode = it },
            ),
            linkedNodeOf("ab"),
        )
        val path = childNode.path()
        assertEquals(3, path.size)
        assertEquals(listOf("a", "aa", "aab"), path.map { it.value })
    }
}
