package ru.vs.core.factoryGenerator

/**
 * Переносит аргумент из конструктора фабрики в аргументы create.
 * @see GenerateFactory
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ByCreate
