package ru.vs.control.feature.splashScreen.ui.screen.splashScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenSharedTransition
import ru.vs.core.sharedElementTransition.WithLocalSharedElementTransition
import ru.vs.core.uikit.icons.Logo

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SplashScreenContent(viewModel: SplashScreenViewModel, modifier: Modifier) {
    val state by viewModel.state.collectAsState()
    Box(modifier.background(MaterialTheme.colorScheme.primary)) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WithLocalSharedElementTransition {
                Icon(
                    Logo,
                    contentDescription = null,
                    Modifier
                        .size(128.dp)
                        .sharedElement(
                            rememberSharedContentState(SplashScreenSharedTransition.LOGO_ID),
                            it,
                        ),
                )
            }

            val isProgressVisible = when (state) {
                SplashScreenViewState.FastLoading -> false
                SplashScreenViewState.LongLoading -> true
            }

            AnimatedVisibility(isProgressVisible) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
