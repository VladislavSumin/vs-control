package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.core.uikit.icons.Logo

@Composable
internal fun WelcomeScreenContent(viewModel: WelcomeScreenViewModel, modifier: Modifier) {
    Box(modifier) {
        Column(Modifier.align(Alignment.Center)) {
            Icon(Logo, contentDescription = null, Modifier.size(128.dp))
            Text("Welcome to Control")
            Button(onClick = viewModel::onClickContinue) {
                Text("to content")
            }
        }
    }
}
