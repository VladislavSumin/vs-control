package ru.vs.control

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware

class App : Application(), DIAware {
    private var _di: DI? = null
    override val di: DI
        get() = _di!!

    override fun onCreate() {
        super.onCreate()
        _di = preInit().di
        InitLogger.i("App#onCreate()")
    }
}
