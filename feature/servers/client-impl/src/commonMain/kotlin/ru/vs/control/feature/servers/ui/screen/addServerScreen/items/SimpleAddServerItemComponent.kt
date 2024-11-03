package ru.vs.control.feature.servers.ui.screen.addServerScreen.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

/**
 * Простой компонент добавления сервера (без дополнительной логики), по сути просто кнопка.
 */
internal class SimpleAddServerItemComponent(
    val item: AddServerItem.Simple,
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Card(modifier) {
            Row(
                Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 6.dp,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(item.icon, null)
                Column(Modifier.padding(start = 8.dp)) {
                    Text(
                        item.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        item.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

private val AddServerItem.Simple.title: String
    get() = when (this) {
        AddServerItem.AddServerByUrl -> "Добавить вручную"
        AddServerItem.AddServerByQrCode -> "Сканировать QR-код"
        AddServerItem.AddLocalServer -> "Включить локальный сервер"
    }

private val AddServerItem.Simple.subtitle: String
    get() = when (this) {
        AddServerItem.AddServerByUrl -> "По доменному имени или ip адресу"
        AddServerItem.AddServerByQrCode -> "QR код можно отобразить в другом клиенте"
        AddServerItem.AddLocalServer -> "Можно использовать это устройство в качестве сервера умного дома"
    }

private val AddServerItem.Simple.icon: ImageVector
    get() = when (this) {
        AddServerItem.AddServerByUrl -> Icons.Default.Language
        AddServerItem.AddServerByQrCode -> Icons.Default.QrCode
        AddServerItem.AddLocalServer -> Icons.Default.Dns
    }
