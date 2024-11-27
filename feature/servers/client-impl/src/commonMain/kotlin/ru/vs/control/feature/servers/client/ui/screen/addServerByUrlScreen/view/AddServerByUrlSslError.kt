package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.core.uikit.paddings.defaultCardContentPadding

@Composable
internal fun AddServerByUrlSslError(
    bottomButton: @Composable () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.defaultCardContentPadding()) {
            Text("Ошибка SSL.")
            Button(onClick = {}) {
                Text("Безопасность для слабых духом")
            }
            bottomButton()
            Spacer(modifier = Modifier.height(300.dp))
        }
    }
}
