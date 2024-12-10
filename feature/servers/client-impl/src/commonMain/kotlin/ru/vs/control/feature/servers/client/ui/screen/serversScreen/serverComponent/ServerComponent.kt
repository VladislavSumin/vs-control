package ru.vs.control.feature.servers.client.ui.screen.serversScreen.serverComponent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vs.control.feature.servers.client.repository.Server
import ru.vs.core.decompose.Component
import ru.vs.core.decompose.ComposeComponent

/**
 * Отображает информацию о конкретном сервере.
 */
internal class ServerComponent(
    private val server: Server,
    context: ComponentContext,
) : Component(context), ComposeComponent {

    val key: Long get() = server.id.raw

    @Composable
    override fun Render(modifier: Modifier) {
        Text("Server name = ${server.name}")
    }
}
