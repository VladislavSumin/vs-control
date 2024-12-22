package ru.vs.rsub

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KType
import kotlin.reflect.typeOf

sealed interface RSubServerSubscription {
    val type: KType
    val argumentTypes: List<KType>

    interface SuspendSub<T> : RSubServerSubscription {
        suspend fun get(arguments: List<Any?>): T
    }

    interface FlowSub<T> : RSubServerSubscription {
        fun get(arguments: List<Any?>): Flow<T>
    }

    companion object {
        inline fun <reified T> createSuspend(
            argumentTypes: List<KType>,
            crossinline method: suspend (arguments: List<Any?>) -> T,
        ): SuspendSub<T> {
            return object : SuspendSub<T> {
                override val type: KType = typeOf<T>()
                override val argumentTypes: List<KType> = argumentTypes

                override suspend fun get(arguments: List<Any?>): T {
                    return method(arguments)
                }
            }
        }

        inline fun <reified T> createFlow(
            argumentTypes: List<KType>,
            crossinline flow: (arguments: List<Any?>) -> Flow<T>,
        ): FlowSub<T> {
            return object : FlowSub<T> {
                override val type: KType = typeOf<T>()

                override val argumentTypes: List<KType> = argumentTypes

                override fun get(arguments: List<Any?>): Flow<T> {
                    return flow(arguments)
                }
            }
        }
    }
}
