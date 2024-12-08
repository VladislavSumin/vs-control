package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewModel
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewState
import vs_control.feature.servers.client_impl.generated.resources.Res
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_back
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_check_connection
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_login_button

/**
 * Многофункциональная кнопка далее, умеет рисовать свое состояние в зависимости от переданного в нее [state]
 */
@Composable
internal fun AddServerByUrlNextButton(
    viewModel: AddServerByUrlViewModel,
    state: AddServerByUrlViewState,
    modifier: Modifier = Modifier,
) {
    val isEnabled = when (state) {
        is AddServerByUrlViewState.EnterUrl -> state.isCheckConnectionButtonEnabled
        is AddServerByUrlViewState.CheckingConnection,
        is AddServerByUrlViewState.EnterCredentials,
        is AddServerByUrlViewState.ConnectionError,
        is AddServerByUrlViewState.CheckingCredentials,
        -> true
    }
    Button(
        onClick = {
            when (state) {
                is AddServerByUrlViewState.EnterUrl -> viewModel.onClickCheckConnection()
                is AddServerByUrlViewState.EnterCredentials -> viewModel.onClickLogin()
                is AddServerByUrlViewState.ConnectionError -> viewModel.onSslErrorClickBack()
                is AddServerByUrlViewState.CheckingConnection,
                is AddServerByUrlViewState.CheckingCredentials,
                -> Unit
            }
        },
        modifier = modifier
            // Явно задавать высоту необходимо, что бы вертикальные размеры кнопки не менялись при shared transition.
            .height(ButtonDefaults.MinHeight)
            .widthIn(min = 256.dp),
        enabled = isEnabled,
    ) {
        AnimatedContent(
            state,
            contentKey = { it::class },
        ) { state ->
            when (state) {
                is AddServerByUrlViewState.CheckingConnection,
                is AddServerByUrlViewState.CheckingCredentials,
                -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )

                is AddServerByUrlViewState.EnterUrl -> Text(
                    stringResource(Res.string.add_server_by_url_screen_server_check_connection),
                    maxLines = 1,
                )

                is AddServerByUrlViewState.EnterCredentials -> Text(
                    stringResource(Res.string.add_server_by_url_screen_server_login_button),
                    maxLines = 1,
                )

                is AddServerByUrlViewState.ConnectionError -> Text(
                    stringResource(Res.string.add_server_by_url_screen_server_back),
                    maxLines = 1,
                )
            }
        }
    }
}
