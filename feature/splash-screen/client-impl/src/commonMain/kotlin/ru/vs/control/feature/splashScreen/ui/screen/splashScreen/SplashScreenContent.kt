package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.core.uikit.icons.Logo

@Composable
internal fun SplashScreenContent(viewModel: SplashScreenViewModel, modifier: Modifier) {
    val state by viewModel.state.collectAsState()
    Box(modifier) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                Logo,
                contentDescription = null,
                Modifier.size(128.dp),
            )

            val isProgressVisible = when (state) {
                SplashScreenViewState.FastLoading -> false
                SplashScreenViewState.LongLoading -> true
            }

            AnimatedVisibility(isProgressVisible) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
internal fun SplashScreenContentPreview() {
    SplashScreenContent(SplashScreenViewModelPreview(true), Modifier.fillMaxSize())
}
