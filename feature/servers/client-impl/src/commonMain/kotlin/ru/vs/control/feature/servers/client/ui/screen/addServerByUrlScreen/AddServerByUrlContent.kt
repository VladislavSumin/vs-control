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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlErrorSheet
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlLoginPassword
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlNextButton
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlServerInfo
import ru.vs.control.feature.servers.client.ui.screen.addServerByUrlScreen.view.AddServerByUrlServerUrlField

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
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
            ServerUrlContent(viewModel, state)

            LoginPasswordContent(viewModel, state)

            Spacer(modifier.weight(1f))

            NextButtonContent(viewModel, state)
        }
    }
}

@Composable
private fun ServerUrlContent(
    viewModel: AddServerByUrlViewModel,
    state: AddServerByUrlViewState,
) {
    when (state) {
        is AddServerByUrlViewState.EnterUrl -> AddServerByUrlServerUrlField(
            state.url,
            onUrlChange = viewModel::onServerUrlChanged,
            onClickEnter = viewModel::onClickCheckConnection,
            isEnabled = true,
            error = state.urlError,
        )

        is AddServerByUrlViewState.CheckingConnection,
        is AddServerByUrlViewState.ConnectionError,
        -> AddServerByUrlServerUrlField(state.url, isEnabled = false)

        is AddServerByUrlViewState.ServerInfoProvider -> AddServerByUrlServerInfo(
            state.serverInfo,
            state.url,
            Modifier.fillMaxWidth(),
            onClickEdit = viewModel::onClickEdit,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun NextButtonContent(
    viewModel: AddServerByUrlViewModel,
    state: AddServerByUrlViewState,
) {
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
                is AddServerByUrlViewState.CheckingCredentials,
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

                is AddServerByUrlViewState.ConnectionError -> AddServerByUrlErrorSheet(state.error) {
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

                is AddServerByUrlViewState.LoginError -> AddServerByUrlErrorSheet(state.error) {
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

@Composable
private fun LoginPasswordContent(viewModel: AddServerByUrlViewModel, state: AddServerByUrlViewState) {
    AnimatedContent(state, contentKey = { it::class }) { state ->
        when (state) {
            is AddServerByUrlViewState.CheckingConnection,
            is AddServerByUrlViewState.EnterUrl,
            is AddServerByUrlViewState.ConnectionError,
            -> Unit

            is AddServerByUrlViewState.EnterCredentials -> AddServerByUrlLoginPassword(
                viewModel,
                state.login,
                state.password,
                enabled = true,
            )

            is AddServerByUrlViewState.CheckingCredentials -> AddServerByUrlLoginPassword(
                viewModel,
                state.login,
                state.password,
                enabled = false,
            )

            is AddServerByUrlViewState.LoginError -> AddServerByUrlLoginPassword(
                viewModel,
                state.login,
                state.password,
                enabled = false,
            )
        }
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
