package ru.vs.convention.android

import ru.vs.configuration.projectConfiguration
import ru.vs.utils.android

/**
 * Базовая настройка android плагина без привязки к конкретной имплементации (application/library/итд).
 */

val configuration = project.projectConfiguration

android {
    setCompileSdkVersion(configuration.core.android.compileSdk)

    defaultConfig {
        minSdk = configuration.core.android.minSdk
        targetSdk = configuration.core.android.targetSdk
    }

    compileOptions {
        val version = JavaVersion.toVersion(configuration.core.jvmVersion)
        sourceCompatibility = version
        targetCompatibility = version
    }
}
