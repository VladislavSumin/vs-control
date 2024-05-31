package ru.vs.convention

/**
 * Настройки по умолчанию для jetbrains compose плагина.
 */

plugins {
//    id("org.jetbrains.compose") apply false
//    id("org.jetbrains.kotlin.plugin.compose") apply false
}

// TODO баг с "BuildScopeServices has been closed"
// issue 1 (закрыто, но не исправлено) - https://github.com/JetBrains/compose-multiplatform/issues/3933
// issue 2 (открыто) - https://github.com/gradle/gradle/issues/27099
// На основе этого сделан этот workaround.
if (project.name != "gradle-kotlin-dsl-accessors") {
    plugins { apply { id("org.jetbrains.compose") } }
    plugins { apply { id("org.jetbrains.kotlin.plugin.compose") } }
}


// val libs = rootProject.the<LibrariesForLibs>()
// val kotlinVersion: String = libs.versions.kotlin.core.get()
// compose {
//      // Версия compose compiler плагина (должна соответствовать версии языка)
//      kotlinCompilerPlugin.set(dependencies.compiler.forKotlin(kotlinVersion))
//      // Игнорирование возможной несовместимости версий compose и версии языка kotlin
//      kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=2.0.0-RC1")
// }
