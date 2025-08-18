import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import org.kodein.di.instance
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ru.vs.control.feature.appInfo.client.domain.AppInfoInteractor
import ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen.RootScreenFactory
import ru.vs.control.preInit
import ru.vs.core.decompose.context.DefaultVsComponentContext

fun main() {
    // Что-то на яблочном, нужно для того что бы compose заработал.
    NSApplication.sharedApplication()

    val di = preInit()
    val appName = di.instance<AppInfoInteractor>().appName

    // Создаем рутовый decompose lifecycle.
    // TODO Реализовать нормальный lifecycle для macos
    val lifecycle = LifecycleRegistry()
    val defaultContext = DefaultComponentContext(lifecycle)
    val vsContext = DefaultVsComponentContext(defaultContext, "macos")

    // Создаем рутовый decompose компонент.
    val rootComponentFactory = di.instance<RootScreenFactory>()
    val rootComponent = rootComponentFactory.create(vsContext)

    lifecycle.resume()

    Window(
        title = appName,
    ) {
        rootComponent.Render(Modifier.fillMaxSize())
    }

    // Что-то на яблочном, нужно для того что бы compose заработал.
    NSApp?.run()
}
