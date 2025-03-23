package ru.vs.control.feature.servers.client.ui.screen.addServerScreen.items

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
import org.jetbrains.compose.resources.stringResource
import ru.vladislavsumin.core.decompose.compose.ComposeComponent
import ru.vs.control.feature.servers.client.ui.screen.addServerScreen.AddServerViewModel
import ru.vs.core.uikit.paddings.defaultCardContentPadding
import vs_control.feature.servers.client_impl.generated.resources.Res
import vs_control.feature.servers.client_impl.generated.resources.add_server_item_by_qr_code_description
import vs_control.feature.servers.client_impl.generated.resources.add_server_item_by_qr_code_title
import vs_control.feature.servers.client_impl.generated.resources.add_server_item_by_url_description
import vs_control.feature.servers.client_impl.generated.resources.add_server_item_by_url_title
import vs_control.feature.servers.client_impl.generated.resources.add_server_item_embedded_description
import vs_control.feature.servers.client_impl.generated.resources.add_server_item_embedded_title
import vs_control.feature.servers.client_impl.generated.resources.add_server_item_not_supported

/**
 * Простой компонент добавления сервера (без дополнительной логики), по сути просто кнопка.
 */
internal class SimpleAddServerItemComponent(
    private val viewModel: AddServerViewModel,
    private val item: AddServerItem.Simple,
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        val isSupported = item.isSupported
        Card(
            onClick = { viewModel.onClickSimpleItem(item) },
            modifier,
            enabled = isSupported,
        ) {
            Row(
                Modifier.defaultCardContentPadding(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(item.icon, null)
                Column(Modifier.padding(start = 8.dp)) {
                    Text(
                        item.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (isSupported) {
                        Text(
                            item.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    } else {
                        Text(
                            stringResource(Res.string.add_server_item_not_supported),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}

private val AddServerItem.Simple.title: String
    @Composable
    get() = when (this) {
        is AddServerItem.AddServerByUrl -> stringResource(Res.string.add_server_item_by_url_title)
        is AddServerItem.AddServerByQrCode -> stringResource(Res.string.add_server_item_by_qr_code_title)
        is AddServerItem.AddEmbeddedServer -> stringResource(Res.string.add_server_item_embedded_title)
        is AddServerItem.AddPrebuildServer -> name
    }

private val AddServerItem.Simple.subtitle: String
    @Composable
    get() = when (this) {
        is AddServerItem.AddServerByUrl -> stringResource(Res.string.add_server_item_by_url_description)
        is AddServerItem.AddServerByQrCode -> stringResource(Res.string.add_server_item_by_qr_code_description)
        is AddServerItem.AddEmbeddedServer -> stringResource(Res.string.add_server_item_embedded_description)
        is AddServerItem.AddPrebuildServer -> url
    }

private val AddServerItem.Simple.icon: ImageVector
    get() = when (this) {
        is AddServerItem.AddServerByUrl -> Icons.Default.Language
        is AddServerItem.AddServerByQrCode -> Icons.Default.QrCode
        is AddServerItem.AddEmbeddedServer -> Icons.Default.Dns
        is AddServerItem.AddPrebuildServer -> Icons.Default.Language
    }

private val AddServerItem.Simple.isSupported: Boolean
    get() = when (this) {
        is AddServerItem.AddPrebuildServer,
        is AddServerItem.AddServerByQrCode,
        is AddServerItem.AddServerByUrl,
        -> true

        is AddServerItem.AddEmbeddedServer -> isSupported
    }
