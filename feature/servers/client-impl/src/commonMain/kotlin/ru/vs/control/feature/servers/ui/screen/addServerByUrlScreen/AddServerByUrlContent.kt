package ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.view.AddServerByUrlNextButton
import ru.vs.control.feature.servers.ui.screen.addServerByUrlScreen.view.AddServerByUrlSslError

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Suppress("LongMethod") // TODO fix
internal fun AddServerByUrlContent(
    viewModel: AddServerByUrlViewModel,
    modifier: Modifier,
) {
    Scaffold(
        modifier,
        topBar = { AppBar(viewModel) },
    ) {
        Column(
            Modifier
                .padding(it)
                .imePadding()
                .fillMaxSize(),
        ) {
            val state = viewModel.state.collectAsState().value
            when (state) {
                is AddServerByUrlViewState.EnterUrl -> ServerUrlInputContent(
                    viewModel,
                    state.url,
                    isEnabled = true,
                )

                is AddServerByUrlViewState.CheckConnection -> ServerUrlInputContent(
                    viewModel,
                    state.url,
                    isEnabled = false,
                )

                is AddServerByUrlViewState.SslError -> ServerUrlInputContent(
                    viewModel,
                    state.url,
                    isEnabled = false,
                )

                is AddServerByUrlViewState.EnterCredentials -> {
                    ServerUrlInputContent(
                        viewModel,
                        state.url,
                        isEnabled = false,
                        showEdit = true,
                    )
                }
            }

            AnimatedContent(state, contentKey = { it::class }) { state ->
                when (state) {
                    is AddServerByUrlViewState.CheckConnection -> Unit
                    is AddServerByUrlViewState.EnterUrl -> Unit
                    is AddServerByUrlViewState.EnterCredentials -> LoginPassword()
                    is AddServerByUrlViewState.SslError -> Unit
                }
            }

            Spacer(modifier.weight(1f))

            val bottomButton = remember {
                movableContentOf<AddServerByUrlViewModel, AddServerByUrlViewState> { viewModel, state ->
                    AddServerByUrlNextButton(viewModel, state)
                }
            }

            AnimatedContent(state, contentKey = { it::class }) { state ->
                when (state) {
                    is AddServerByUrlViewState.CheckConnection,
                    is AddServerByUrlViewState.EnterCredentials,
                    is AddServerByUrlViewState.EnterUrl,
                        -> bottomButton(viewModel, state)

                    is AddServerByUrlViewState.SslError -> AddServerByUrlSslError { bottomButton(viewModel, state) }
                }
            }
        }
    }
}

@Composable
private fun ServerUrlInputContent(
    viewModel: AddServerByUrlViewModel,
    url: String,
    isEnabled: Boolean,
    showEdit: Boolean = false,
) {
    OutlinedTextField(
        value = url,
        onValueChange = { viewModel.onServerUrlChanged(it) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        enabled = isEnabled,
        label = { Text("Server url") },
        placeholder = { Text("https://control.vs:443") },
        trailingIcon = {
            AnimatedContent(showEdit) { showEdit ->
                if (showEdit) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, null)
                    }
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors()
            .copy(
                disabledTrailingIconColor = OutlinedTextFieldDefaults.colors().unfocusedTrailingIconColor,
            ),
    )
}

@Composable
private fun LoginPassword(login: String = "admin", password: String = "testPass") {
    Column {
        OutlinedTextField(
            value = login,
            onValueChange = { },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text("Login") },
        )
        OutlinedTextField(
            value = password,
            onValueChange = { },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = { Text("Password") },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(viewModel: AddServerByUrlViewModel) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = viewModel::onClickBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text("Add server by url")
        },
    )
}
