package ru.vs.control.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.feature.rootScreen.ui.screen.rootScreen.RootScreenFactory

class MainActivity : ComponentActivity(), DIAware {
    override val di: DI by closestDI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultContext = defaultComponentContext()
        val rootComponentFactory = di.direct.instance<RootScreenFactory>()
        val rootComponent = rootComponentFactory.create(defaultContext)

        setContent {
            rootComponent.Render(Modifier.fillMaxSize())
        }
    }
}
