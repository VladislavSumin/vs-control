package ru.vs.control

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // preInit()

    CanvasBasedWindow(
        title = "Control",
        canvasElementId = "root",
    ) {
        TestCompose()
    }

    // InitLogger.i("finish main()")
}
