package ${PACKAGE_NAME}

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vladislavsumin.core.navigation.factoryGenerator.GenerateScreenFactory
import ru.vs.core.decompose.context.VsComponentContext
import ru.vs.core.decompose.context.VsScreen

@GenerateScreenFactory
internal class ${NAME}Screen(
    viewModelFactory: ${NAME}ViewModelFactory,
    context: VsComponentContext,
    params: ${NAME}ScreenParams,
) : VsScreen(context) {
    private val viewModel = viewModel { viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        ${NAME}Content(modifier)
    }
}
