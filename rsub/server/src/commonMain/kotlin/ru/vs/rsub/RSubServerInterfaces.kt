package ru.vs.rsub

import kotlin.reflect.KClass

/**
 * Генерирует прокси классы реализующие [RSubServerInterface] для переданных [interfaces].
 * Пакет прокси класса будет равен пакету интерфейса, а название будет InterfaceNameRSubServerProxy
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FILE)
annotation class RSubServerInterfaces(val interfaces: Array<KClass<*>>)
