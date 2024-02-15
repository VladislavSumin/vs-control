package ru.vs.control

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import org.kodein.di.instance
import ru.vs.control.feature.appInfo.domain.AppInfoInteractor

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val di = preInit()
    val appName = di.instance<AppInfoInteractor>().appName

    onWasmReady {
        CanvasBasedWindow(
            title = appName,
            canvasElementId = "root",
        ) {
            TestCompose()
        }
    }
}
