package ru.vs.core.splash

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import ru.vs.core.coroutines.setMain
import ru.vs.core.decompose.BaseComponentTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SplashTest : BaseComponentTest() {
    /**
     * Тест проверяет очередность переключения состояний сплеша.
     */
    @Test
    fun testStateMachine() = runTest {
        setMain()

        val splash = context.testChildSplash(this)

        splash.assertSplash()
        testScheduler.runCurrent()
        splash.assertSplash()
        splash.initialize()
        testScheduler.runCurrent()
        splash.assertSplash()
        splash.onLeakingContentReady()
        splash.assertContent()
    }

    /**
     * Тест проверяет корректную работу сплеша при условии мгновенной синхронной инициализации приложения.
     */
    @Test
    fun testImmediatelyInitialize() = runTest {
        setMain()

        val splash = context.testChildSplash(this, isInitialized = true, instantContentReady = true)
        runCurrent()
        splash.assertContent()
    }

    /**
     * Тест проверяет корректное восстановление состояние после смерти процесса или иных обстоятельствах, когда
     * [com.arkivanov.essenty.statekeeper.StateKeeper] сохранил состояние.
     */
    @Test
    fun testStateRestore() = runTest {
        setMain()

        val splash = context.testChildSplash(
            scope = this,
            isInitialized = true,
            instantContentReady = true,
        )

        // Что бы вызвалось переключение на content.
        runCurrent()
        splash.assertContent()
        val state = splash.splash.value.child.instance.keepStateUniqueValue

        recreateContext(RecreateContextType.ProcessDeath)

        val secondSplash = context.testChildSplash(
            scope = this,
            isInitialized = false,
            instantContentReady = true,
        )

        testScheduler.runCurrent()
        secondSplash.initialize()
        testScheduler.runCurrent()
        secondSplash.assertContent()
        assertEquals(state, secondSplash.splash.value.child.instance.keepStateUniqueValue)
    }
}
