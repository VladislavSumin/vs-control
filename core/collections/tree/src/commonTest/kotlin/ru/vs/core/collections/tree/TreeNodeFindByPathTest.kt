package ru.vs.core.collections.tree

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TreeNodeFindByPathTest {
    @Test
    fun singleNodePathTest() {
        val node = nodeOf("a")
        val find = node.findByPath(listOf("a")) { it }
        assertEquals(node, find)
    }

    @Test
    fun nodePathTest() {
        val node = nodeOf(
            "a",
            nodeOf(
                "aa",
                nodeOf("aaa"),
                nodeOf("aab"),
            ),
            nodeOf("ab"),
        )
        val find = node.findByPath(listOf("a", "aa", "aab")) { it }
        assertEquals("aab", find!!.value)
    }

    @Test
    fun nodePathNotFoundTest() {
        val node = nodeOf(
            "a",
            nodeOf(
                "aa",
                nodeOf("aaa"),
                nodeOf("aab"),
            ),
            nodeOf("ab"),
        )
        val find = node.findByPath(listOf("a", "aa", "aac")) { it }
        assertNull(find)
    }
}
