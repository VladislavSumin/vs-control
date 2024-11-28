package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import vs_control.feature.servers.client_impl.generated.resources.Res
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_url

/**
 * Поле ввода адреса сервера.
 *
 * @param url текущий введенный url.
 * @param onUrlChange вызывается при изменении url.
 * @param isEnabled разрешено ли в данный момент изменять url.
 * @param showEdit показывать ли кнопку edit.
 */
@Composable
internal fun AddServerByUrlServerUrlField(
    url: String,
    onUrlChange: (String) -> Unit = {},
    isEnabled: Boolean,
    showEdit: Boolean = false,
) {
    OutlinedTextField(
        value = url,
        onValueChange = onUrlChange,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = isEnabled,
        label = { Text(stringResource(Res.string.add_server_by_url_screen_server_url)) },
        prefix = { Text("https://") },
        placeholder = { Text("control.vs:443") },
        trailingIcon = {
            AnimatedContent(showEdit) { showEdit ->
                if (showEdit) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, null)
                    }
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors().copy(
            disabledTrailingIconColor = OutlinedTextFieldDefaults.colors().unfocusedTrailingIconColor,
        ),
    )
}
