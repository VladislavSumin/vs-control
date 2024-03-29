package ru.vs.core.splash

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.builtins.serializer
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
        // TODO вынести в экстеншен?
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // TODO вынести в экстеншен?
        val lifecycle = LifecycleRegistry()
        val defaultContext = DefaultComponentContext(lifecycle)
        lifecycle.resume()

        var leakingOnContentReady: (() -> Unit)? = null

        val splash = defaultContext.childSplash(
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
        leakingOnContentReady!!()
        assertEquals(ChildSplashConfiguration.Content, splash.value.child.configuration)
    }

    /**
     * Тест проверяет корректную работу сплеша при условии мгновенной синхронной инициализации приложения.
     */
    @Test
    fun testImmediatelyInitialize() = runTest {
        // TODO вынести в экстеншен?
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // TODO вынести в экстеншен?
        val lifecycle = LifecycleRegistry()
        val defaultContext = DefaultComponentContext(lifecycle)
        lifecycle.resume()

        val splash = defaultContext.childSplash(
            scope = this,
            awaitInitialization = { /* мгновенно возвращаем управление */ },
            splashComponentFactory = { },
            contentComponentFactory = { onContentReady, _ ->
                onContentReady()
            },
        )

        assertEquals(ChildSplashConfiguration.Content, splash.value.child.configuration)
    }

    @Test
    fun testStateRestore() = runTest {
        // TODO вынести в экстеншен?
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // TODO вынести в экстеншен?
        val lifecycle = LifecycleRegistry()
        val stateKeeper = StateKeeperDispatcher()
        val defaultContext = DefaultComponentContext(lifecycle, stateKeeper)
        lifecycle.resume()

        defaultContext.childSplash(
            scope = this,
            awaitInitialization = { /* мгновенно возвращаем управление */ },
            splashComponentFactory = { },
            contentComponentFactory = { onContentReady, context ->
                onContentReady()
                context.stateKeeper.register(TEST_KEY, String.serializer()) { TEST_VALUE }
            },
        )

        val savedState = stateKeeper.save()
        lifecycle.destroy()

        val lifecycle2 = LifecycleRegistry()
        val stateKeeper2 = StateKeeperDispatcher(savedState)
        val defaultContext2 = DefaultComponentContext(lifecycle2, stateKeeper2)
        lifecycle.resume()

        var isContentComponentFactoryCalled = false

        defaultContext2.childSplash(
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

    // TODO написать тесты на сохранение состояния композиции
}
