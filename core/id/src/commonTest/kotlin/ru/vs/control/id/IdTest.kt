package ru.vs.control.id

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class IdTest {
    @Test
    fun createWithCorrectSimpleId() {
        val id = Id(TEST_RAW_ID_SIMPLE)
        assertIs<Id.SimpleId>(id)
    }

    @Test
    fun createWithCorrectPartsId() {
        val id = Id(TEST_RAW_ID_WITH_PARTS)
        assertIs<Id.SimpleId>(id)
    }

    @Test
    fun createWithCorrectComplexId() {
        val id = Id(TEST_RAW_ID_COMPLEX)
        assertIs<Id.SimpleId>(id)
    }

    @Test
    fun createWithIncorrectIdEmptyString() {
        assertFailsWith<IllegalStateException> {
            Id("")
        }
    }

    @Test
    fun createWithIncorrectIdStartAtSlash() {
        assertFailsWith<IllegalStateException> {
            Id("/a/b/c")
        }
    }

    @Test
    fun createWithIncorrectIdEndAtSlash() {
        assertFailsWith<IllegalStateException> {
            Id("a/b/c/")
        }
    }

    @Test
    fun createWithIncorrectIdContainsSpace() {
        assertFailsWith<IllegalStateException> {
            Id("a a/b")
        }
    }

    @Test
    fun createWithIncorrectIdContainsUpperRegister() {
        assertFailsWith<IllegalStateException> {
            Id("aA")
        }
    }

    @Test
    fun rawIdEqualsCreationId() {
        val id = Id(TEST_RAW_ID_SIMPLE)
        assertEquals(TEST_RAW_ID_SIMPLE, id.rawId)
    }

    @Test
    fun checkEquals() {
        val id1 = Id(TEST_RAW_ID_SIMPLE)
        val id2 = Id(TEST_RAW_ID_SIMPLE)
        assertEquals(id1.hashCode(), id2.hashCode())
        assertEquals(id1, id2)
    }

    @Test
    fun testDoubleId() {
        val id1 = Id(TEST_RAW_ID_SIMPLE)
        val id2 = Id(TEST_RAW_ID_WITH_PARTS)
        val doubleId = Id(TEST_RAW_ID_DOUBLE)
        assertIs<Id.DoubleId>(doubleId)
        assertEquals(id1, doubleId.firstPart)
        assertEquals(id2, doubleId.secondPart)
    }

    @Test
    fun testTripleId() {
        val id1 = Id(TEST_RAW_ID_SIMPLE)
        val id2 = Id(TEST_RAW_ID_WITH_PARTS)
        val id3 = Id(TEST_RAW_ID_COMPLEX)
        val doubleId = Id(TEST_RAW_ID_TRIPLE)
        assertIs<Id.TripleId>(doubleId)
        assertEquals(id1, doubleId.firstPart)
        assertEquals(id2, doubleId.secondPart)
        assertEquals(id3, doubleId.thirdPart)
    }

    companion object {
        private const val TEST_RAW_ID_SIMPLE = "test_id"
        private const val TEST_RAW_ID_WITH_PARTS = "test/id/with/same/path123"
        private const val TEST_RAW_ID_COMPLEX = "test/id/with_same_path"
        private const val TEST_RAW_ID_DOUBLE = "$TEST_RAW_ID_SIMPLE#$TEST_RAW_ID_WITH_PARTS"
        private const val TEST_RAW_ID_TRIPLE = "$TEST_RAW_ID_SIMPLE#$TEST_RAW_ID_WITH_PARTS#$TEST_RAW_ID_COMPLEX"
    }
}
