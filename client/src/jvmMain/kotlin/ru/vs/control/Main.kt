package ru.vs.control

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.kodein.di.instance
import ru.vs.control.feature.appInfo.client.domain.AppInfoInteractor
import ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen.RootScreenFactory
import ru.vs.core.decompose.context.DefaultVsComponentContext
import kotlin.system.exitProcess
import ru.vladislavsumin.core.decompose.compose.runOnUiThread

/**
 * Точка входа в приложение.
 */
fun main() {
    setExitOnUncaughtException()

    val di = preInit()
    val appName = di.instance<AppInfoInteractor>().appName

    // Создаем рутовый Decompose lifecycle.
    val lifecycle = LifecycleRegistry()

    // Создаем рутовый компонент.
    val rootComponent = runOnUiThread {
        val defaultContext = DefaultComponentContext(lifecycle)
        val vsContext = DefaultVsComponentContext(defaultContext, "jvm")
        val rootComponentFactory = di.instance<RootScreenFactory>()
        rootComponentFactory.create(vsContext)
    }

    application {
        // Связываем рутовый Decompose lifecycle с жизненным циклом окна.
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            title = appName,
            onCloseRequest = ::exitApplication,
        ) {
            rootComponent.Render(Modifier.fillMaxSize())
        }
    }
}

fun setExitOnUncaughtException() {
    val handler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
        handler?.uncaughtException(thread, throwable)
        throwable.printStackTrace()
        exitProcess(1)
    }
}
