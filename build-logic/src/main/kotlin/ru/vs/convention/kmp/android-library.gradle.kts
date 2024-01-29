package ru.vs.convention.kmp

import ru.vs.configuration.projectConfiguration
import ru.vs.utils.android

/**
 * Базовая настройка android-library таргета для KMP.
 */

plugins {
    id("ru.vs.convention.kmp.common")
    id("com.android.library")
    // мы не можем использовать ru.vs.convention.android.library здесь, поскольку этот плагин подключат kotlin плагин,
    // а нам нужен kotlin-multiplatform плагин.
    id("ru.vs.convention.android.base")
    id("ru.vs.convention.android.default-namespace")
}

kotlin {
    androidTarget {
        // Настраиваем версию jvm для сборки андроид модулей.
        compilations.all {
            kotlinOptions {
                jvmTarget = project.projectConfiguration.core.jvmVersion
            }
        }
    }
}

android {
    // Указываем директорию для поиска в ней AndroidManifest.xml.
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}
