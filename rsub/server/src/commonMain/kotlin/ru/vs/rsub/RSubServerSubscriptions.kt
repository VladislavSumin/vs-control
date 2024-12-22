package ru.vs.rsub

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class RSubServerSubscriptions(val interfaces: Array<KClass<*>>)
