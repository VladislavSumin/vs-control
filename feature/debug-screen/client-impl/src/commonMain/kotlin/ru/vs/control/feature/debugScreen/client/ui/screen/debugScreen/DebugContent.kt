package ru.vs.control.feature.debugScreen.client.ui.screen.debugScreen

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.decompose.compose.ComposeComponent

@Composable
internal fun DebugContent(
    viewModel: DebugViewModel,
    umlComponent: ComposeComponent,
    modifier: Modifier,
) {
    Scaffold(
        modifier,
        topBar = { AppBar(viewModel) },
    ) {
        umlComponent.Render(
            Modifier
                .padding(it)
                .fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(viewModel: DebugViewModel) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = viewModel::onClickBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text("Debug")
        },
    )
}
