package ru.vs.control

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.kodein.di.instance
import ru.vs.control.feature.appInfo.domain.AppInfoInteractor

fun main() {
    val di = preInit()
    val appName = di.instance<AppInfoInteractor>().appName

    application {
        Window(
            title = appName,
            onCloseRequest = ::exitApplication,
        ) {
            TestCompose()
        }
    }
}
