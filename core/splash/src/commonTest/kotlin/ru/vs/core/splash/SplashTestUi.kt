package ru.vs.core.splash

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vladislavsumin.core.decompose.test.BaseComponentTest
import ru.vs.core.compose.advanceTimeOneFrameBeforeBy
import ru.vs.core.coroutines.setMain
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val ANIMATION_DURATION = 5_000

@OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
class SplashTestUi : BaseComponentTest() {
    /**
     * Тест проверяет, что после инициализации splash сменяется контентом.
     * Данный тест не проверяет анимацию перехода между сплешем и контентом.
     */
    @Test
    fun testContentChange() = runTest {
        setMain()
        val isInitialized = MutableStateFlow(false)
        runComposeUiTest {
            val splash = context.childSplash(
                scope = this@runTest,
                isInitialized = isInitialized,
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
            isInitialized.value = true

            // Проверяем состояние Content
            onNodeWithTag(SplashScreenComponent.TAG).assertDoesNotExist()
            onNodeWithTag(ContentScreenComponent.TAG).assertExists()
        }
    }

    /**
     * Тест проверяет наличие анимации смены splash на content.
     */
    @Test
    fun testAnimatedContentChange() = runTest {
        setMain()
        val isInitialized = MutableStateFlow(false)
        runComposeUiTest {
            val splash = context.childSplash(
                scope = this@runTest,
                isInitialized = isInitialized,
                splashComponentFactory = { SplashScreenComponent(it) },
                contentComponentFactory = { onContentReady, context ->
                    onContentReady()
                    ContentScreenComponent(context)
                },
            )

            setContent {
                Children(
                    splash,
                    splashExitTransition = fadeOut(tween(durationMillis = ANIMATION_DURATION)),
                ) {
                    it.Render(Modifier.fillMaxSize())
                }
            }

            mainClock.autoAdvance = false

            // Проверяем состояние Splash.
            onNodeWithTag(SplashScreenComponent.TAG).assertExists()
            onNodeWithTag(ContentScreenComponent.TAG).assertDoesNotExist()

            isInitialized.value = true

            //  Стартует анимация, должны быть одновременно видны оба экрана.
            mainClock.advanceTimeByFrame()

            // Проверяем состояние во время анимации
            onNodeWithTag(SplashScreenComponent.TAG).assertExists()
            onNodeWithTag(ContentScreenComponent.TAG).assertExists()

            // Проматываем анимацию на один кадр раньше чем она должна завершиться
            mainClock.advanceTimeOneFrameBeforeBy(ANIMATION_DURATION.toLong())

            // Проверяем состояние в последний кадр анимации
            onNodeWithTag(SplashScreenComponent.TAG).assertExists()
            onNodeWithTag(ContentScreenComponent.TAG).assertExists()

            // Проматываем последний кадр
            mainClock.advanceTimeByFrame()

            // Проверяем состояние Content
            onNodeWithTag(SplashScreenComponent.TAG).assertDoesNotExist()
            onNodeWithTag(ContentScreenComponent.TAG).assertExists()
        }
    }

    /**
     * Тест проверяет восстановление состояния контента после пересоздания композиции.
     */
    @Test
    fun testRestoreState() = runTest {
        setMain()

        val registry1 = SaveableStateRegistry(emptyMap()) { true }
        var data: Map<String, List<Any?>>? = null

        // Тестируемое состояние, при первом запуске генерируется случайно, при повторном должно восстановиться.
        var randomValue: CharSequence? = null

        val isInitialized = MutableStateFlow(false)

        runComposeUiTest {
            val splash = context.childSplash(
                scope = this@runTest,
                isInitialized = isInitialized,
                splashComponentFactory = { SplashScreenComponent(it) },
                contentComponentFactory = { onContentReady, context ->
                    onContentReady()
                    ContentScreenComponent(context)
                },
            )

            setTestContent(splash, registry1)

            isInitialized.value = true

            randomValue = onNodeWithTag(ContentScreenComponent.SAVEABLE_CONTENT_TAG)
                .fetchSemanticsNode()
                .config[SemanticsProperties.Text].first()

            // Сохранять нужно внутри runComposeUiTest, иначе composer уничтожится и сохранять будет нечего.
            data = registry1.performSave()
        }

        assertTrue(data!!.isNotEmpty(), "No saved data")

        val registry2 = SaveableStateRegistry(data) { true }

        val isInitialized2 = MutableStateFlow(false)
        recreateContext(RecreateContextType.ProcessDeath)

        runComposeUiTest {
            val splash = context.childSplash(
                scope = this@runTest,
                isInitialized = isInitialized2,
                splashComponentFactory = { SplashScreenComponent(it) },
                contentComponentFactory = { onContentReady, context ->
                    onContentReady()
                    ContentScreenComponent(context)
                },
            )

            setTestContent(splash, registry2)

            isInitialized2.value = true

            val restoredValue = onNodeWithTag(ContentScreenComponent.SAVEABLE_CONTENT_TAG)
                .fetchSemanticsNode()
                .config[SemanticsProperties.Text].first()

            assertEquals(randomValue, restoredValue)
        }
    }
}

/**
 * Так как тест должен проходить даже при использовании автоматически сгенерированных ключей, то мы должны заставить
 * compose генерировать одинаковые хеши в нужных нам местах. Для этого ВЕСЬ compose код в обеих частях теста должен быть
 * одинаковым, причем буквально это должен быть один и тот же код, иначе компилятор создаст для него разные хеши.
 *
 * Лямбда внутри [ComposeUiTest.setContent] тоже считается, поэтому выносим весь код в общую функцию, тогда все хеши
 * генерируются единообразно.
 */
@OptIn(ExperimentalTestApi::class)
private fun <T : ComposeComponent> ComposeUiTest.setTestContent(
    splash: Value<ChildSplash<T>>,
    registry: SaveableStateRegistry,
) {
    setContent {
        CompositionLocalProvider(
            LocalSaveableStateRegistry provides registry,
        ) {
            Children(splash) {
                it.Render(Modifier.fillMaxSize())
            }
        }
    }
}
