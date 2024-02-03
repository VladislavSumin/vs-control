package ru.vs.control

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    preInit()

    application {
        Window(
            onCloseRequest = ::exitApplication
        ) {
            TestCompose()
        }
    }

    InitLogger.i("finish main()")
}
