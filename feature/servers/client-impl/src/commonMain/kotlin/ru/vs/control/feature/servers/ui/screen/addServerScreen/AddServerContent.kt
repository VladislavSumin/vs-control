package ru.vs.control.feature.servers.ui.screen.addServerScreen

import androidx.compose.foundation.layout.Column
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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.Value
import ru.vs.core.decompose.ComposeComponent

@Composable
internal fun AddServerContent(
    modifier: Modifier,
    viewModel: AddServerViewModel,
    list: Value<List<ComposeComponent>>,
) {
    Scaffold(
        modifier,
        topBar = { AppBar(viewModel) },
    ) {
        val state = list.subscribeAsState()
        Column(Modifier.padding(it)) {
            state.value.forEach { component -> component.Render(Modifier) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(viewModel: AddServerViewModel) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = viewModel::onClickBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        },
        title = {
            Text("Add Server")
        },
    )
}
