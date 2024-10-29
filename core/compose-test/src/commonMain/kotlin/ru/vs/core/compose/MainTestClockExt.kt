package ru.vs.core.compose

import androidx.compose.ui.test.MainTestClock

private const val DEFAULT_FRAME_TIME: Long = 16L

/**
 * Время одного кадра в compose ui тестах.
 * Получить его напрямую нельзя, но можно найти его в коде androidx.compose.ui.test.SkikoComposeUiTest
 */
val MainTestClock.frameTime: Long
    get() = DEFAULT_FRAME_TIME

/**
 * Перематывает время на один кадр меньше переданного времени.
 */
fun MainTestClock.advanceTimeOneFrameBeforeBy(milliseconds: Long) {
    check(milliseconds >= frameTime)
    advanceTimeBy(milliseconds - frameTime)
}
