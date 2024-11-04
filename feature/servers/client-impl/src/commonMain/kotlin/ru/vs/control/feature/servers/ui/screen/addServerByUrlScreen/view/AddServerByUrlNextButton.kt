package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlViewModel
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlViewState

/**
 * Многофункциональная кнопка далее, умеет рисовать свое состояние в зависимости от переданного в нее [state]
 */
@Composable
internal fun AddServerByUrlNextButton(
    viewModel: AddServerByUrlViewModel,
    state: AddServerByUrlViewState,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            when (state) {
                is AddServerByUrlViewState.EnterUrl -> viewModel.onClickCheckConnection()
                is AddServerByUrlViewState.CheckConnection -> Unit
                is AddServerByUrlViewState.EnterCredentials -> viewModel.onClickLogin()
                is AddServerByUrlViewState.SslError -> viewModel.onSslErrorClickBack()
            }
        },
        modifier = modifier,
    ) {
        AnimatedContent(
            state,
            contentKey = { it::class },
        ) { state ->
            when (state) {
                is AddServerByUrlViewState.CheckConnection -> CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                )

                is AddServerByUrlViewState.EnterUrl -> Text("Проверить соединение")
                is AddServerByUrlViewState.EnterCredentials -> Text("Войти")
                is AddServerByUrlViewState.SslError -> Text("Назад к безопасности")
            }
        }
    }
}
