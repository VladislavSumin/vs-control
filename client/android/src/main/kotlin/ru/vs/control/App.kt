package ru.vs.control

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        preInit()
        InitLogger.i("App#onCreate()")
    }
}
