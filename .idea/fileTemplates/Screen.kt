package ${PACKAGE_NAME}

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class ${NAME}ScreenFactory(
    private val viewModelFactory: ${NAME}ViewModelFactory,
) : ScreenFactory<${NAME}ScreenParams, ${NAME}Screen> {
    override fun create(context: ScreenContext, params: ${NAME}ScreenParams): ${NAME}Screen {
        return ${NAME}Screen(viewModelFactory, context, params)
    }
}

internal class ${NAME}Screen(
    viewModelFactory: ${NAME}ViewModelFactory,
    context: ScreenContext,
    params: ${NAME}ScreenParams,
) : Screen, ScreenContext by context {
    private val viewModel = instanceKeeper.getOrCreate{ viewModelFactory.create() }

    @Composable
    override fun Render(modifier: Modifier) {
        ${NAME}Content(modifier)
    }
}
