package ru.vs.convention

import org.gradle.accessors.dm.LibrariesForLibs

/**
 * Настройки по умолчанию для jetbrains compose плагина.
 */

plugins {
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

val libs = rootProject.the<LibrariesForLibs>()
val kotlinVersion: String = libs.versions.kotlin.core.get()
compose {
    // Версия compose compiler плагина (должна соответствовать версии языка)
    // kotlinCompilerPlugin.set(dependencies.compiler.forKotlin(kotlinVersion))

    // Игнорирование возможной несовместимости версий compose и версии языка kotlin
    // kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=2.0.0-RC1")
}
