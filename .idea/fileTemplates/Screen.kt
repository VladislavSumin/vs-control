package ${PACKAGE_NAME}

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.vs.core.navigation.screen.Screen
import ru.vs.core.navigation.screen.ScreenContext
import ru.vs.core.navigation.screen.ScreenFactory

internal class ${NAME}ScreenFactory : ScreenFactory<${NAME}ScreenParams, ${NAME}Screen> {
    override fun create(context: ScreenContext, params: ${NAME}ScreenParams): ${NAME}Screen {
        return ${NAME}Screen(context, params)
    }
}

internal class ${NAME}Screen(
    context: ScreenContext,
    params: ${NAME}ScreenParams,
) : Screen, ScreenContext by context {
    @Composable
    override fun Render(modifier: Modifier) {
        Box(modifier.background(Color.Cyan)) {
            Text("${NAME}Screen", Modifier.align(Alignment.Center))
        }
    }
}
