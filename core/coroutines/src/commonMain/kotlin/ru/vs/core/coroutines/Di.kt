package ru.vs.core.coroutines

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.core.di.Modules

fun Modules.coreCoroutines() = DI.Module("core-coroutines") {
    bindSingleton<DispatchersProvider> { DefaultDispatchersProvider }
}
