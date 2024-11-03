package ru.vs.core.factoryGenerator

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class GenerateFactory(val factoryInterface: KClass<*> = Any::class)
