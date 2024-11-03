package ${PACKAGE_NAME}

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext

@GenerateScreenFactory
internal class ${NAME}Screen(
    viewModelFactory: ${NAME}ViewModelFactory,
    context: ScreenContext,
    params: ${NAME}ScreenParams,
) : Screen(context), ScreenContext by context {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        ${NAME}Content(modifier)
    }
}
