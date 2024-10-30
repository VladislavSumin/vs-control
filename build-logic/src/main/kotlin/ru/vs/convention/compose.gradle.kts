package ru.vs.convention

import org.jetbrains.compose.ExperimentalComposeLibrary

/**
 * Настройки по умолчанию для jetbrains compose плагина.
 */

plugins {
    id("ru.vs.convention.kmp.common")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

// val kotlinVersion: String = libs.versions.kotlin.core.get()
// compose {
//      // Версия compose compiler плагина (должна соответствовать версии языка)
//      kotlinCompilerPlugin.set(dependencies.compiler.forKotlin(kotlinVersion))
//      // Игнорирование возможной несовместимости версий compose и версии языка kotlin
//      kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=2.0.0-RC1")
// }

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Работа с ресурсами (аля строками) через xml.
            implementation(compose.components.resources)
        }
        commonTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }
}
