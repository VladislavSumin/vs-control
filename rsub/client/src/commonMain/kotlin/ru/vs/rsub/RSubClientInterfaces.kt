package ru.vs.rsub

import kotlin.reflect.KClass

/**
 * Генерирует клиентские реализации для переданных [interfaces].
 * Пакет имплементации будет равен пакету интерфейса, а название будет InterfaceNameRSubImpl
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FILE)
annotation class RSubClientInterfaces(val interfaces: Array<KClass<*>>)
