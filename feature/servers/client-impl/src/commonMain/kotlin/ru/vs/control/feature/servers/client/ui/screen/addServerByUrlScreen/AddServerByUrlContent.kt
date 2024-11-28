package ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlNextButton
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlServerUrlField
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlSslError

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
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp,
                )
                .imePadding()
                .fillMaxSize(),
        ) {
            val state = viewModel.state.collectAsState().value
            when (state) {
                is AddServerByUrlViewState.EnterUrl -> AddServerByUrlServerUrlField(
                    state.url,
                    onUrlChange = viewModel::onServerUrlChanged,
                    onClickEnter = viewModel::onClickCheckConnection,
                    isEnabled = true,
                )

                is AddServerByUrlViewState.CheckingConnection,
                is AddServerByUrlViewState.SslError,
                -> AddServerByUrlServerUrlField(state.url, isEnabled = false)

                is AddServerByUrlViewState.EnterCredentials -> AddServerByUrlServerUrlField(
                    state.url,
                    isEnabled = false,
                    showEdit = true,
                )
            }

            AnimatedContent(state, contentKey = { it::class }) { state ->
                when (state) {
                    is AddServerByUrlViewState.CheckingConnection,
                    is AddServerByUrlViewState.EnterUrl,
                    is AddServerByUrlViewState.SslError,
                    -> Unit

                    is AddServerByUrlViewState.EnterCredentials -> LoginPassword()
                }
            }

            Spacer(modifier.weight(1f))

            SharedTransitionLayout(Modifier.fillMaxWidth()) {
                AnimatedContent(
                    state,
                    Modifier.fillMaxWidth(),
                    contentKey = { it::class },
                ) { state ->
                    val animatedScope = this
                    when (state) {
                        is AddServerByUrlViewState.CheckingConnection,
                        is AddServerByUrlViewState.EnterCredentials,
                        is AddServerByUrlViewState.EnterUrl,
                        -> Box(Modifier.fillMaxWidth()) {
                            AddServerByUrlNextButton(
                                viewModel,
                                state,
                                Modifier
                                    .sharedElement(
                                        rememberSharedContentState(NEXT_BUTTON_SHARED_TRANSITION_KEY),
                                        animatedScope,
                                    )
                                    .align(Alignment.Center),
                            )
                        }

                        is AddServerByUrlViewState.SslError -> AddServerByUrlSslError {
                            AddServerByUrlNextButton(
                                viewModel,
                                state,
                                Modifier
                                    .sharedElement(
                                        rememberSharedContentState(NEXT_BUTTON_SHARED_TRANSITION_KEY),
                                        animatedScope,
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
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

private const val NEXT_BUTTON_SHARED_TRANSITION_KEY = "next_button"
