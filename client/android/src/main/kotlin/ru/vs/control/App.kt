package ru.vs.control

import android.app.Application
import ru.vs.core.logger.api.logger
import ru.vs.core.logger.manager.LoggerManager
import ru.vs.core.logger.platform.initDefault

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LoggerManager.initDefault()
        val logger = logger("main")
        logger.i { "Hello android" }
    }
}
