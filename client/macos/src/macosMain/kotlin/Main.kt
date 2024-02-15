import androidx.compose.ui.window.Window
import org.kodein.di.instance
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ru.vs.control.TestCompose
import ru.vs.control.feature.appInfo.domain.AppInfoInteractor
import ru.vs.control.preInit

fun main() {
    NSApplication.sharedApplication()

    val di = preInit()
    val appName = di.instance<AppInfoInteractor>().appName

    Window(
        title = appName,
    ) {
        TestCompose()
    }

    NSApp?.run()
}
