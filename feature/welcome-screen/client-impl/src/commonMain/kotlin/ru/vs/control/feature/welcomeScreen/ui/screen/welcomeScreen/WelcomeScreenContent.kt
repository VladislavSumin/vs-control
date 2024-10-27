package ru.vs.control.feature.welcomeScreen.ui.screen.welcomeScreen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.splashScreen.ui.screen.splashScreen.SplashScreenSharedTransition
import ru.vs.core.sharedElementTransition.WithLocalSharedElementTransition
import ru.vs.core.uikit.icons.Logo

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
            "Welcome to Control",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            "Давайте добавим первое подключение?",
            Modifier.padding(top = 2.dp),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.weight(1f))

        Column(Modifier.width(IntrinsicSize.Min)) {
            Button(
                onClick = viewModel::onClickContinue,
                Modifier.fillMaxWidth(),
            ) { Text("Добавить") }

            OutlinedButton(
                onClick = viewModel::onClickSkip,
                Modifier.fillMaxWidth(),
            ) { Text("Пропустить") }
        }
    }
}
