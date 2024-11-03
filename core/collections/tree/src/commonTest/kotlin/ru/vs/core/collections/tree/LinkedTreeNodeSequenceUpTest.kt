package ru.vs.core.collections.tree

import kotlin.test.Test
import kotlin.test.assertEquals

class LinkedTreeNodeSequenceUpTest {
    @Test
    fun singleNodeSequenceUpTest() {
        val node = linkedNodeOf("a")
        val path = node.asSequenceUp().toList()
        assertEquals(1, path.size)
        assertEquals("a", path.first().value)
    }

    @Test
    fun nodeSequenceUpTest() {
        val childNode: LinkedTreeNode<String>
        linkedNodeOf(
            "a",
            linkedNodeOf(
                "aa",
                linkedNodeOf("aaa"),
                linkedNodeOf("aab"),
            ).also { childNode = it },
            linkedNodeOf("ab"),
        )
        val path = childNode.asSequenceUp().toList()
        // assertEquals(5, path.size)
        assertEquals(listOf("aa", "aaa", "aab", "a", "ab"), path.map { it.value })
    }
}
