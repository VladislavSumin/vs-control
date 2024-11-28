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
import androidx.compose.ui.unit.min
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewModel
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewState

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
        is AddServerByUrlViewState.SslError,
        -> true
    }
    Button(
        onClick = {
            when (state) {
                is AddServerByUrlViewState.EnterUrl -> viewModel.onClickCheckConnection()
                is AddServerByUrlViewState.CheckingConnection -> Unit
                is AddServerByUrlViewState.EnterCredentials -> viewModel.onClickLogin()
                is AddServerByUrlViewState.SslError -> viewModel.onSslErrorClickBack()
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
                is AddServerByUrlViewState.CheckingConnection -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )

                is AddServerByUrlViewState.EnterUrl -> Text("Проверить соединение", maxLines = 1)
                is AddServerByUrlViewState.EnterCredentials -> Text("Войти", maxLines = 1)
                is AddServerByUrlViewState.SslError -> Text("Назад", maxLines = 1)
            }
        }
    }
}
