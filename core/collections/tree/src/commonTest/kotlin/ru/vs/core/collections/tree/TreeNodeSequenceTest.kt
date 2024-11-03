package ru.vs.core.collections.tree

import kotlin.test.Test
import kotlin.test.assertEquals

class TreeNodeSequenceTest {
    @Test
    fun singleNodeSequenceTest() {
        val node = nodeOf("a")
        val nodeSequence = node.asSequence().toList()
        assertEquals(1, nodeSequence.size)
        assertEquals("a", nodeSequence.first().value)
    }

    @Test
    fun nodeSequenceTest() {
        val node = nodeOf(
            "a",
            nodeOf(
                "aa",
                nodeOf("aaa"),
                nodeOf("aab"),
            ),
            nodeOf("ab"),
        )
        val nodeSequence = node.asSequence().toList()
        assertEquals(5, nodeSequence.size)
        assertEquals(listOf("a", "aa", "ab", "aaa", "aab"), nodeSequence.map { it.value })
    }
}
