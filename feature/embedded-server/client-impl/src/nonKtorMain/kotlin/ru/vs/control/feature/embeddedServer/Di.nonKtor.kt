package ru.vs.control.feature.embeddedServer

import org.kodein.di.DI
import ru.vs.core.di.Modules

actual fun Modules.featureEmbeddedServerPlatform(): DI.Module = DI.Module("feature-embedded-server-platform") {
    // no dependencies
}
