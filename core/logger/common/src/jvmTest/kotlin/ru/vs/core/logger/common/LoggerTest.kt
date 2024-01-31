package ru.vs.core.logger.common

import io.mockk.spyk
import io.mockk.verify
import kotlin.test.Test

class LoggerTest {
    @Test
    fun loggerAcceptLogsWithUpperFilterLevel() {
        val logger = spyk(TestLogger())
        logger.e { "Test" }
        verify { logger.accessLogInternal(LogLevel.ERROR, "Test") }
    }

    @Test
    fun loggerDeclineLogsWithLoverFilterLevel() {
        val logger = spyk(TestLogger())
        logger.d { "Test" }
        verify(exactly = 0) { logger.accessLogInternal(any(), any()) }
    }
}

private class TestLogger : Logger() {
    override var logLevel: LogLevel = LogLevel.INFO

    public override fun logInternal(level: LogLevel, msg: String) {
        // no op
    }

    public override fun logInternal(level: LogLevel, throwable: Throwable, msg: String) {
        // no op
    }
}
