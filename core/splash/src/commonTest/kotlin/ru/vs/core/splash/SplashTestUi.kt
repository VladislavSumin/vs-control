package ru.vs.core.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import ru.vs.core.coroutines.setMain

private const val FAKE_DELAY = 10_000L

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class SplashTestUi {
    /**
     * Тест проверяет, что после инициализации splash сменяется контентом.
     * Данный тест не проверяет анимацию перехода между сплешем и контентом.
     */
    @Test
    fun testContentChange() = runTest {
        setMain()
        runComposeUiTest {
            // TODO вынести в экстеншен?
            val lifecycle = LifecycleRegistry()
            val defaultContext = DefaultComponentContext(lifecycle)
            lifecycle.resume()

            val splash = defaultContext.childSplash(
                scope = this@runTest,
                awaitInitialization = { delay(FAKE_DELAY) },
                splashComponentFactory = { SplashScreenComponent(it) },
                contentComponentFactory = { onContentReady, context ->
                    onContentReady()
                    ContentScreenComponent(context)
                },
            )

            setContent {
                Children(splash) {
                    it.Render(Modifier.fillMaxSize())
                }
            }

            // Проверяем состояние Splash.
            onNodeWithTag(SplashScreenComponent.TAG).assertExists()
            onNodeWithTag(ContentScreenComponent.TAG).assertDoesNotExist()

            // Тут крутим main часики, так как awaitInitialization запускается на Dispatchers Main.
            advanceTimeBy(FAKE_DELAY)

            mainClock.advanceTimeByFrame()

            // Проверяем состояние Content
            onNodeWithTag(SplashScreenComponent.TAG).assertDoesNotExist()
            onNodeWithTag(ContentScreenComponent.TAG).assertExists()
        }
    }
}
