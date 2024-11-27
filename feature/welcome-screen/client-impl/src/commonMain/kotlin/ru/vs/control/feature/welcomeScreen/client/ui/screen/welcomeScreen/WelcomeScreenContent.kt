package ru.vs.control.feature.welcomeScreen.client.ui.screen.welcomeScreen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.vs.control.feature.splashScreen.client.ui.screen.splashScreen.SplashScreenSharedTransition
import ru.vs.core.sharedElementTransition.WithLocalSharedElementTransition
import ru.vs.core.uikit.icons.Logo
import vs_control.feature.welcome_screen.client_impl.generated.resources.Res
import vs_control.feature.welcome_screen.client_impl.generated.resources.welcome_screen_continue
import vs_control.feature.welcome_screen.client_impl.generated.resources.welcome_screen_title

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun WelcomeScreenContent(viewModel: WelcomeScreenViewModel, modifier: Modifier) {
    Column(
        modifier
            .safeDrawingPadding()
            .padding(
                vertical = 24.dp,
                horizontal = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))

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

        Text(
            stringResource(Res.string.welcome_screen_title),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.weight(2f))

        Button(
            onClick = viewModel::onClickContinue,
            Modifier
                .widthIn(min = 256.dp),
        ) { Text(stringResource(Res.string.welcome_screen_continue)) }
    }
}
