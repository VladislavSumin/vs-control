package ru.vs.convention

/**
 * Настройки по умолчанию для jetbrains compose плагина.
 */

plugins {
    id("org.jetbrains.compose")
}

compose {
    // Версия compose compiler плагина (должна соответствовать версии языка)
    kotlinCompilerPlugin.set(dependencies.compiler.forKotlin("2.0.0-Beta4"))

    // Игнорирование возможной несовместимости версий compose и версии языка kotlin
    // kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=2.0.0-Beta4")
}
