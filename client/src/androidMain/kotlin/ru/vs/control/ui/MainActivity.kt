package ru.vs.control.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.channels.Channel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.feature.rootScreen.client.ui.screen.rootScreen.RootScreenFactory
import ru.vs.core.decompose.context.DefaultVsComponentContext

class MainActivity : ComponentActivity(), DIAware {
    override val di: DI by closestDI()

    /**
     * Канал в который отправляются все пришедшие диплинки.
     */
    private val deeplinkChannel = Channel<String>(capacity = Channel.BUFFERED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Обрабатываем стартовый deeplink только если это холодный запуск приложения.
        // В противном случае мы уже обработали этот диплинк при первом запуске.
        if (savedInstanceState == null) {
            intent?.data?.let { onDeeplink(it.toString()) }
        }

        val defaultContext = defaultComponentContext()
        val vsComponentContext = DefaultVsComponentContext(defaultContext, "android")
        val rootComponentFactory = di.direct.instance<RootScreenFactory>()
        val rootComponent = rootComponentFactory.create(vsComponentContext, deeplinkChannel)

        setContent {
            rootComponent.Render(Modifier.fillMaxSize())
        }
    }

    private fun onDeeplink(deeplink: String) {
        deeplinkChannel.trySend(deeplink).getOrThrow()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { onDeeplink(it.toString()) }
    }
}
