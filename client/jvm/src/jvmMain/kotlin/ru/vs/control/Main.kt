package ru.vs.control

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    preInit()

    application {
        Window(
            title = "Control",
            onCloseRequest = ::exitApplication
        ) {
            TestCompose()
        }
    }
}
