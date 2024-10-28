package ${PACKAGE_NAME}

import androidx.compose.runtime.Stable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import ru.vs.core.decompose.ViewModel

internal class ${NAME}ViewModelFactory {
    fun create(): ${NAME}ViewModel {
        return ${NAME}ViewModel()
    }
}

@Stable
internal class ${NAME}ViewModel : ViewModel(){
    val state: StateFlow<AddServerViewState> = TODO()
    val events: Channel<AddServerEvents> = TODO()
}
