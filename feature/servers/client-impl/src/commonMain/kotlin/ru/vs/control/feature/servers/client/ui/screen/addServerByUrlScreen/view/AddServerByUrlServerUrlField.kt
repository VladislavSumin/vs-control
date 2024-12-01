package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import org.jetbrains.compose.resources.stringResource
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewModel
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewState
import vs_control.feature.servers.client_impl.generated.resources.Res
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_url
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_url_error
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_url_path

/**
 * Поле ввода адреса сервера.
 *
 * @param url текущий введенный url.
 * @param onUrlChange вызывается при изменении url.
 * @param onClickEnter вызывается при нажатии кнопки enter на клавиатуре при фокусе в это поле ввода.
 * @param isEnabled разрешено ли в данный момент изменять url.
 * @param showEdit показывать ли кнопку edit.
 * @param error ошибка валидации введенного url.
 */
@Composable
internal fun AddServerByUrlServerUrlField(
    url: String,
    onUrlChange: (String) -> Unit = {},
    onClickEnter: () -> Unit = {},
    isEnabled: Boolean,
    showEdit: Boolean = false,
    error: AddServerByUrlViewState.UrlError = AddServerByUrlViewState.UrlError.None,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    val isError = error.isError()

    OutlinedTextField(
        value = url,
        onValueChange = onUrlChange,
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        enabled = isEnabled,
        isError = isError,
        label = { Text(stringResource(Res.string.add_server_by_url_screen_server_url)) },
        prefix = { Text(AddServerByUrlViewModel.SCHEME) },
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
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors().copy(
            disabledTrailingIconColor = OutlinedTextFieldDefaults.colors().unfocusedTrailingIconColor,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions { onClickEnter() },
        supportingText = {
            AnimatedVisibility(
                visible = isError,
                enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom, clip = false),
                exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom, clip = false),
            ) {
                // Необходимо для корректной анимации скрытия ошибки, если просто брать текущий текст,
                // то при скрытии не будет ясно текст какой ошибки показывать.
                var text by remember { mutableStateOf("") }
                if (isError) {
                    text = error.text
                }

                Text(text)
            }
        },
    )
}

private val AddServerByUrlViewState.UrlError.text: String
    @Composable
    get() = when (this) {
        AddServerByUrlViewState.UrlError.None -> error("No text for None error state")
        AddServerByUrlViewState.UrlError.UrlWithPathOrQuery -> {
            stringResource(Res.string.add_server_by_url_screen_server_url_path)
        }

        AddServerByUrlViewState.UrlError.MalformedUrl -> {
            stringResource(Res.string.add_server_by_url_screen_server_url_error)
        }
    }
