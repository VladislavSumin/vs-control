package ru.vs.control

import android.app.Application
import android.content.Context
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindProvider
import ru.vs.core.di.Modules

class App : Application(), DIAware {
    private var _di: DI? = null
    override val di: DI
        get() = _di!!

    override fun onCreate() {
        super.onCreate()
        _di = preInit(Modules.android()).di
        InitLogger.i("App#onCreate()")
    }

    private fun Modules.android() = DI.Module("android") {
        bindProvider<Context> { this@App }
    }
}
