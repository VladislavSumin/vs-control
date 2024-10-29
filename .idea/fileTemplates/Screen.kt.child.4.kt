package ${PACKAGE_NAME}

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun ${NAME}Content(
    modifier: Modifier,
) {
    Box(modifier.background(Color.Cyan)) {
        Text("${NAME}Screen", Modifier.align(Alignment.Center))
    }
}