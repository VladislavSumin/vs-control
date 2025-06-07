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
import javax.swing.SwingUtilities
import kotlin.system.exitProcess

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
        val rootComponentFactory = di.instance<RootScreenFactory>()
        rootComponentFactory.create(defaultContext)
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

/**
 * See https://arkivanov.github.io/Decompose/getting-started/quick-start/
 * See https://github.com/arkivanov/Decompose/blob/master/sample/app-desktop/src/jvmMain/kotlin/com/arkivanov/sample/app/Utils.kt
 */
@Suppress("TooGenericExceptionCaught")
internal fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}

fun setExitOnUncaughtException() {
    val handler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
        handler?.uncaughtException(thread, throwable)
        throwable.printStackTrace()
        exitProcess(1)
    }
}
