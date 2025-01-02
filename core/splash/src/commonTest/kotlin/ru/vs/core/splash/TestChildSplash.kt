package ru.vs.core.splash

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import ru.vs.core.decompose.ChildTestComponentContext
import kotlin.test.assertEquals

class TestChildSplash(
    parentContext: ComponentContext,
    scope: CoroutineScope,
    isInitialized: Boolean,
    instantContentReady: Boolean,
) {
    var splashFactoryCalls = 0
        private set
    var contentFactoryCalls = 0
        private set

    var onLeakingContentReady: () -> Unit = {}
        private set

    private val isInitialized = MutableStateFlow(isInitialized)

    val splash = parentContext.childSplash(
        scope = scope,
        isInitialized = this.isInitialized,
        splashComponentFactory = {
            splashFactoryCalls++
            ChildTestComponentContext(Unit, "splash", it)
        },
        contentComponentFactory = { onContentReady, context ->
            contentFactoryCalls++
            if (instantContentReady) {
                onContentReady()
            } else {
                onLeakingContentReady = onContentReady
            }
            ChildTestComponentContext(Unit, "content", context)
        },
    )

    fun initialize() {
        isInitialized.value = true
    }

    fun assertSplash() {
        assertEquals(ChildSplashConfiguration.Splash, splash.value.child.configuration)
    }

    fun assertContent() {
        assertEquals(ChildSplashConfiguration.Content, splash.value.child.configuration)
    }
}

fun ComponentContext.testChildSplash(
    scope: CoroutineScope,
    isInitialized: Boolean = false,
    instantContentReady: Boolean = false,
): TestChildSplash {
    return TestChildSplash(this, scope, isInitialized, instantContentReady)
}
