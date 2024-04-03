package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vs.core.uikit.icons.Logo

@Composable
internal fun SplashScreenContent(viewModel: SplashScreenViewModel, modifier: Modifier) {
    val state by viewModel.state.collectAsState()
    Box(modifier.background(Color.Cyan)) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(Logo, null, Modifier.size(128.dp))
            Spacer(Modifier.height(8.dp))
            Text("Апка загружается")
            Spacer(Modifier.height(8.dp))
            when (state) {
                SplashScreenViewState.FastLoading -> Unit
                SplashScreenViewState.LongLoading -> CircularProgressIndicator()
            }
        }
    }
}
