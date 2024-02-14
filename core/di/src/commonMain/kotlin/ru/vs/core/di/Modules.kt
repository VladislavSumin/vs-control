package ru.vs.core.di

/**
 * Объект маркер, нужен чтобы удобно группировать модули di под общим префиксом [Modules].
 * Пример использования:
 * ```kotlin
 * fun Modules.coreExample() = DI.Module("core-example"){ ... }
 * ```
 * Правила именования модулей описаны в README.md.
 */
object Modules
