package ${PACKAGE_NAME}

import androidx.compose.runtime.Stable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import ru.vladislavsumin.core.decompose.components.ViewModel
import ru.vladislavsumin.core.factoryGenerator.GenerateFactory

@Stable
@GenerateFactory
internal class ${NAME}ViewModel : ViewModel(){
    val state: StateFlow<${NAME}ViewState> = TODO()
    val events: Channel<${NAME}Events> = TODO()
}
