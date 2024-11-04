package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlViewModel
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.AddServerByUrlViewState

/**
 * Многофункциональная кнопка далее, умеет рисовать свое состояние в зависимости от переданного в нее [state]
 */
@Composable
internal fun ColumnScope.AddServerByUrlNextButton(
    viewModel: AddServerByUrlViewModel,
    state: State<AddServerByUrlViewState>,
) {
    val state = state.value
    Button(
        onClick = {
            when (state) {
                is AddServerByUrlViewState.EnterUrl -> viewModel.onClickCheckConnection()
                is AddServerByUrlViewState.CheckConnection -> Unit
                is AddServerByUrlViewState.EnterCredentials -> viewModel.onClickLogin()
            }
        },
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(8.dp),
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
            }
        }
    }
}
