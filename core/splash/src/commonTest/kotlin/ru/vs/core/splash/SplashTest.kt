package ru.vs.core.splash

import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.statekeeper.SerializableContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import ru.vs.core.coroutines.setMain
import ru.vs.core.decompose.ResumedTestComponentContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private const val FAKE_DELAY = 1000L
private const val TEST_KEY = "test-key"
private const val TEST_VALUE = "test-value"

@OptIn(ExperimentalCoroutinesApi::class)
class SplashTest {
    /**
     * Тест проверяет очередность переключения состояний сплеша.
     */
    @Test
    fun testStateMachine() = runTest {
        setMain()

        var leakingOnContentReady: (() -> Unit)? = null

        val splash = ResumedTestComponentContext().childSplash(
            scope = this,
            awaitInitialization = { delay(FAKE_DELAY) },
            splashComponentFactory = { },
            contentComponentFactory = { onContentReady, _ ->
                leakingOnContentReady = onContentReady
            },
        )

        assertEquals(ChildSplashConfiguration.Splash, splash.value.child.configuration)
        testScheduler.runCurrent()
        assertEquals(ChildSplashConfiguration.Splash, splash.value.child.configuration)
        advanceTimeBy(FAKE_DELAY)
        testScheduler.runCurrent()
        assertEquals(ChildSplashConfiguration.Splash, splash.value.child.configuration)
        assertNotNull(leakingOnContentReady)
        leakingOnContentReady()
        assertEquals(ChildSplashConfiguration.Content, splash.value.child.configuration)
    }

    /**
     * Тест проверяет корректную работу сплеша при условии мгновенной синхронной инициализации приложения.
     */
    @Test
    fun testImmediatelyInitialize() = runTest {
        setMain()

        val splash = ResumedTestComponentContext().childSplash(
            scope = this,
            awaitInitialization = { /* мгновенно возвращаем управление */ },
            splashComponentFactory = { },
            contentComponentFactory = { onContentReady, _ ->
                onContentReady()
            },
        )

        runCurrent()

        assertEquals(ChildSplashConfiguration.Content, splash.value.child.configuration)
    }

    /**
     * Тест проверяет корректное восстановление состояние после смерти процесса или иных обстоятельствах, когда
     * [com.arkivanov.essenty.statekeeper.StateKeeper] сохранил состояние.
     */
    @Test
    fun testStateRestore() = runTest {
        setMain()

        val context = ResumedTestComponentContext()

        context.childSplash(
            scope = this,
            awaitInitialization = { /* мгновенно возвращаем управление */ },
            splashComponentFactory = { },
            contentComponentFactory = { onContentReady, context ->
                onContentReady()
                context.stateKeeper.register(TEST_KEY, String.serializer()) { TEST_VALUE }
            },
        )

        // Что бы вызвалось переключение на content.
        runCurrent()

        val savedState: SerializableContainer = context.stateKeeperDispatcher.save()
        context.lifecycleRegistry.destroy()

        val context2 = ResumedTestComponentContext(savedState)

        var isContentComponentFactoryCalled = false

        context2.childSplash(
            scope = this,
            awaitInitialization = { delay(FAKE_DELAY) },
            splashComponentFactory = { },
            contentComponentFactory = { _, context ->
                assertEquals(TEST_VALUE, context.stateKeeper.consume(TEST_KEY, String.serializer()))
                isContentComponentFactoryCalled = true
            },
        )

        testScheduler.runCurrent()
        advanceTimeBy(FAKE_DELAY)
        testScheduler.runCurrent()
        assertTrue(isContentComponentFactoryCalled)
    }
}
