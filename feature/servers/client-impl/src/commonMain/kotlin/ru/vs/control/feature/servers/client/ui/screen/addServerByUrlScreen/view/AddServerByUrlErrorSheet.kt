package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.vs.core.uikit.paddings.defaultCardContentPadding
import vs_control.feature.servers.client_impl.generated.resources.Res
import vs_control.feature.servers.client_impl.generated.resources.add_server_by_url_screen_server_connecting_error

@Composable
internal fun AddServerByUrlErrorSheet(
    errorMessage: String,
    bottomButton: @Composable () -> Unit,
) {
    Card {
        Column(
            Modifier.defaultCardContentPadding().fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(Res.string.add_server_by_url_screen_server_connecting_error),
                Modifier.padding(bottom = 4.dp),
            )
            Text(errorMessage, Modifier.padding(bottom = 8.dp))
            bottomButton()
        }
    }
}
