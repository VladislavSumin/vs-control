package ru.vs.control.feature.mainScreen.ui.screen.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun MainContent(
    viewModel: MainViewModel,
    modifier: Modifier,
) {
    Scaffold(
        modifier,
        topBar = { MainAppBar() },
        contentWindowInsets = WindowInsets.statusBars,
    ) { paddings ->
        Box(
            Modifier
                .padding(paddings)
                .fillMaxSize()
                .background(Color.Cyan),
        ) {
            Column(Modifier.align(Alignment.Center)) {
                Text("MainScreen")
                Button(
                    onClick = viewModel::onClickDebug,
                ) {
                    Text("Open Debug")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainAppBar() {
    TopAppBar(
        title = {
            Text("Control")
        },
    )
}
