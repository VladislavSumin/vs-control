package ru.vs.core.logger.platform

import android.util.Log
import ru.vs.core.logger.common.LogLevel
import ru.vs.core.logger.manager.ExternalLogger
import ru.vs.core.logger.manager.ExternalLoggerFactory

internal actual fun createPlatformLoggerFactory(): ExternalLoggerFactory {
    return ExternalLoggerFactory { tag ->
        LogcatExternalLogger(tag)
    }
}

private class LogcatExternalLogger(private val tag: String) : ExternalLogger {
    override fun log(level: LogLevel, msg: String) {
        when (level) {
            LogLevel.TRACE -> Log.v(tag, msg)
            LogLevel.DEBUG -> Log.d(tag, msg)
            LogLevel.INFO -> Log.i(tag, msg)
            LogLevel.WARN -> Log.w(tag, msg)
            LogLevel.ERROR -> Log.e(tag, msg)
            LogLevel.FATAL -> Log.wtf(tag, msg)
            LogLevel.NONE -> Unit // no op
        }
    }

    override fun log(level: LogLevel, throwable: Throwable, msg: String) {
        when (level) {
            LogLevel.TRACE -> Log.v(tag, msg, throwable)
            LogLevel.DEBUG -> Log.d(tag, msg, throwable)
            LogLevel.INFO -> Log.i(tag, msg, throwable)
            LogLevel.WARN -> Log.w(tag, msg, throwable)
            LogLevel.ERROR -> Log.e(tag, msg, throwable)
            LogLevel.FATAL -> Log.wtf(tag, msg, throwable)
            LogLevel.NONE -> Unit // no op
        }
    }
}
