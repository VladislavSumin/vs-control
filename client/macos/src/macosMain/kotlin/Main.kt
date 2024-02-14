import androidx.compose.ui.window.Window
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ru.vs.control.TestCompose
import ru.vs.control.preInit

fun main() {
    NSApplication.sharedApplication()

    preInit()

    Window(
        title = "Control"
    ) {
        TestCompose()
    }

    NSApp?.run()
}
