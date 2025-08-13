package ru.vs.control.feature.mainScreen.client.ui.screen.mainScreen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import ru.vladislavsumin.core.decompose.compose.ComposeComponent

@Composable
internal fun MainContent(
    viewModel: MainViewModel,
    tabNavigation: Value<ChildPages<*, ComposeComponent>>,
    modifier: Modifier,
) {
    Scaffold(
        modifier,
        bottomBar = { BottomBar(viewModel) },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddings ->
        com.arkivanov.decompose.extensions.compose.pages.ChildPages(
            pages = tabNavigation,
            onPageSelected = {},
            Modifier.padding(paddings),
            scrollAnimation = PagesScrollAnimation.Default,
        ) { _, page ->
            page.Render(Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomBar(viewModel: MainViewModel) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = viewModel::onClickDebug,
            icon = { Icon(Icons.Default.Dns, null) },
            label = { Text("Servers") },
        )
        NavigationBarItem(
            selected = false,
            onClick = viewModel::onClickDebug,
            icon = { Icon(Icons.Default.BugReport, null) },
            label = { Text("Debug") },
        )
    }
}
