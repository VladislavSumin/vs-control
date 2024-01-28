package ru.vs.core.logger.common

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogLevelTest {
    @Test
    fun testAllowLog() {
        assertTrue(LogLevel.TRACE.allowLog(LogLevel.TRACE))
        assertTrue(LogLevel.TRACE.allowLog(LogLevel.NONE))
        assertTrue(LogLevel.TRACE.allowLog(LogLevel.INFO))

        assertFalse(LogLevel.WARN.allowLog(LogLevel.INFO))

        assertFalse(LogLevel.NONE.allowLog(LogLevel.TRACE))
        assertFalse(LogLevel.NONE.allowLog(LogLevel.INFO))
        assertFalse(LogLevel.NONE.allowLog(LogLevel.ERROR))
    }

    @Test
    fun testAllowLogNone() {
        // Не скажу, что это хорошее поведение, но в целом отвечает общей логике выше
        assertTrue(LogLevel.NONE.allowLog(LogLevel.NONE))
    }

    @Test
    fun testMerge() {
        assertEquals(LogLevel.NONE, LogLevel.TRACE merge LogLevel.NONE)
        assertEquals(LogLevel.NONE, LogLevel.NONE merge LogLevel.TRACE)

        assertEquals(LogLevel.INFO, LogLevel.INFO merge LogLevel.INFO)

        assertEquals(LogLevel.INFO, LogLevel.INFO merge LogLevel.DEBUG)
        assertEquals(LogLevel.WARN, LogLevel.DEBUG merge LogLevel.WARN)
    }
}
