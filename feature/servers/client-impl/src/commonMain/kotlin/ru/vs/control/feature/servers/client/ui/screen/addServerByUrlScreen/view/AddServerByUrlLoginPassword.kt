package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.AddServerByUrlViewModel

@Composable
internal fun AddServerByUrlLoginPassword(
    viewModel: AddServerByUrlViewModel,
    login: String,
    password: String,
    enabled: Boolean,
) {
    Column {
        OutlinedTextField(
            value = login,
            onValueChange = viewModel::onLoginChanged,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text("Login") },
            enabled = enabled,
        )
        OutlinedTextField(
            value = password,
            onValueChange = viewModel::onPasswordChanged,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text("Password") },
            enabled = enabled,
        )
    }
}
