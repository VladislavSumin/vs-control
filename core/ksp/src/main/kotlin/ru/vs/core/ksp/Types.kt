package ru.vs.core.ksp

import com.squareup.kotlinpoet.ClassName

/**
 * Набор стандартных типов для использования в кодогенерации.
 */
object Types {
    object Coroutines {
        val Flow = ClassName("kotlinx.coroutines.flow", "Flow")
        val CoroutineScope = ClassName("kotlinx.coroutines", "CoroutineScope")
        val GlobalScope = ClassName("kotlinx.coroutines", "GlobalScope")
    }

    object Serialization {
        val ProtoBuf = ClassName("kotlinx.serialization.protobuf", "ProtoBuf")
    }
}
