package ru.vs.control

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.lifecycle.stop
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import org.kodein.di.instance
import org.w3c.dom.Document
import ru.vs.control.feature.appInfo.domain.AppInfoInteractor
import ru.vs.control.feature.rootScreen.ui.screen.rootScreen.RootScreenFactory

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val di = preInit()
    val appName = di.instance<AppInfoInteractor>().appName

    // Создаем рутовый Decompose lifecycle.
    val lifecycle = LifecycleRegistry()
    val defaultContext = DefaultComponentContext(lifecycle)

    // Создаем рутовый Decompose компонент
    val rootComponentFactory = di.instance<RootScreenFactory>()
    val rootComponent = rootComponentFactory.create(defaultContext)

    lifecycle.attachToDocument()

    onWasmReady {
        CanvasBasedWindow(
            title = appName,
            canvasElementId = "root",
        ) {
            rootComponent.Render(Modifier.fillMaxSize())
        }
    }
}

// See https://arkivanov.github.io/Decompose/getting-started/quick-start/
private fun LifecycleRegistry.attachToDocument() {
    fun onVisibilityChanged() {
        if (document.visibilityState == "visible") resume() else stop()
    }

    onVisibilityChanged()

    document.addEventListener(type = "visibilitychange", callback = { onVisibilityChanged() })
}

private val Document.visibilityState: String
    get() = asDynamic().visibilityState.unsafeCast<String>()
